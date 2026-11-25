package org.jetbrains.kotlin.resolve.jvm.checkers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: JavaNullabilityChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\tR\u0011\u0010\n\u001a\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\n\u0010\t¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/checkers/EnhancedNullabilityInfo;", "", "enhancedType", "Lorg/jetbrains/kotlin/types/KotlinType;", "isFromJava", "", "(Lorg/jetbrains/kotlin/types/KotlinType;Z)V", "getEnhancedType", "()Lorg/jetbrains/kotlin/types/KotlinType;", "()Z", "isFromKotlin", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class EnhancedNullabilityInfo {
    private final KotlinType enhancedType;
    private final boolean isFromJava;

    public EnhancedNullabilityInfo(KotlinType enhancedType, boolean z) {
        Intrinsics.checkNotNullParameter(enhancedType, "enhancedType");
        this.enhancedType = enhancedType;
        this.isFromJava = z;
    }

    public final KotlinType getEnhancedType() {
        return this.enhancedType;
    }

    /* JADX INFO: renamed from: isFromJava, reason: from getter */
    public final boolean getIsFromJava() {
        return this.isFromJava;
    }

    public final boolean isFromKotlin() {
        return !this.isFromJava;
    }
}
