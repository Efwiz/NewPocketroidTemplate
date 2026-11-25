package org.jetbrains.kotlin.load.java.sam;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.resolve.sam.SamConversionOracle;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: JvmSamConversionOracle.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0010\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/load/java/sam/JvmSamConversionOracle;", "Lorg/jetbrains/kotlin/resolve/sam/SamConversionOracle;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "(Lorg/jetbrains/kotlin/config/LanguageVersionSettings;)V", "isJavaApplicableCandidate", "", "candidate", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "isPossibleSamType", "samType", "Lorg/jetbrains/kotlin/types/KotlinType;", "shouldRunSamConversionForFunction", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmSamConversionOracle implements SamConversionOracle {
    private final LanguageVersionSettings languageVersionSettings;

    public JvmSamConversionOracle(LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        this.languageVersionSettings = languageVersionSettings;
    }

    @Override // org.jetbrains.kotlin.resolve.sam.SamConversionOracle
    public boolean shouldRunSamConversionForFunction(CallableDescriptor candidate) {
        Intrinsics.checkNotNullParameter(candidate, "candidate");
        if (this.languageVersionSettings.supportsFeature(LanguageFeature.SamConversionForKotlinFunctions)) {
            return true;
        }
        return JavaBasedSamConversionOracle.INSTANCE.shouldRunSamConversionForFunction(candidate);
    }

    @Override // org.jetbrains.kotlin.resolve.sam.SamConversionOracle
    public boolean isPossibleSamType(KotlinType samType) {
        Intrinsics.checkNotNullParameter(samType, "samType");
        return JavaBasedSamConversionOracle.INSTANCE.isPossibleSamType(samType);
    }

    @Override // org.jetbrains.kotlin.resolve.sam.SamConversionOracle
    public boolean isJavaApplicableCandidate(CallableDescriptor candidate) {
        Intrinsics.checkNotNullParameter(candidate, "candidate");
        return JavaBasedSamConversionOracle.INSTANCE.shouldRunSamConversionForFunction(candidate);
    }
}
