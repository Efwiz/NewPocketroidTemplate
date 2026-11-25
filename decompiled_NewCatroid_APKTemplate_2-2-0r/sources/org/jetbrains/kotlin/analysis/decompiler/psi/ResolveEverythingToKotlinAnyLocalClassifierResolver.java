package org.jetbrains.kotlin.analysis.decompiler.psi;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.serialization.deserialization.LocalClassifierTypeSettings;
import org.jetbrains.kotlin.types.SimpleType;

/* JADX INFO: compiled from: DeserializerForDecompilerBase.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0005\u001a\u0004\u0018\u00010\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/analysis/decompiler/psi/ResolveEverythingToKotlinAnyLocalClassifierResolver;", "Lorg/jetbrains/kotlin/serialization/deserialization/LocalClassifierTypeSettings;", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "(Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;)V", "replacementTypeForLocalClassifiers", "Lorg/jetbrains/kotlin/types/SimpleType;", "getReplacementTypeForLocalClassifiers", "()Lorg/jetbrains/kotlin/types/SimpleType;", "decompiler-to-psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ResolveEverythingToKotlinAnyLocalClassifierResolver implements LocalClassifierTypeSettings {
    private final KotlinBuiltIns builtIns;

    public ResolveEverythingToKotlinAnyLocalClassifierResolver(KotlinBuiltIns builtIns) {
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        this.builtIns = builtIns;
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.LocalClassifierTypeSettings
    public SimpleType getReplacementTypeForLocalClassifiers() {
        return this.builtIns.getAnyType();
    }
}
