package org.jetbrains.kotlin.fir.types;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.types.AbstractTypeApproximator;
import org.jetbrains.kotlin.types.TypeApproximatorConfiguration;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;

/* JADX INFO: compiled from: ConeTypeApproximator.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0018\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000bJ\u0018\u0010\f\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000b¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/ConeTypeApproximator;", "Lorg/jetbrains/kotlin/types/AbstractTypeApproximator;", "inferenceContext", "Lorg/jetbrains/kotlin/fir/types/ConeInferenceContext;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "(Lorg/jetbrains/kotlin/fir/types/ConeInferenceContext;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;)V", "approximateToSubType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", ModuleXmlParser.TYPE, "conf", "Lorg/jetbrains/kotlin/types/TypeApproximatorConfiguration;", "approximateToSuperType", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeTypeApproximator extends AbstractTypeApproximator {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ConeTypeApproximator(ConeInferenceContext inferenceContext, LanguageVersionSettings languageVersionSettings) {
        super(inferenceContext, languageVersionSettings);
        Intrinsics.checkNotNullParameter(inferenceContext, "inferenceContext");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
    }

    public final ConeKotlinType approximateToSuperType(ConeKotlinType type, TypeApproximatorConfiguration conf) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(conf, "conf");
        return (ConeKotlinType) super.approximateToSuperType((KotlinTypeMarker) type, conf);
    }

    public final ConeKotlinType approximateToSubType(ConeKotlinType type, TypeApproximatorConfiguration conf) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(conf, "conf");
        return (ConeKotlinType) super.approximateToSubType((KotlinTypeMarker) type, conf);
    }
}
