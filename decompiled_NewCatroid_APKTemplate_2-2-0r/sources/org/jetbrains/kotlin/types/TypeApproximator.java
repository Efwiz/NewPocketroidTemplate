package org.jetbrains.kotlin.types;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.resolve.calls.components.ClassicTypeSystemContextForCS;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;

/* JADX INFO: compiled from: TypeApproximator.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0016\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fJ \u0010\r\u001a\u0004\u0018\u00010\b2\u0006\u0010\u000e\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\fJ\u0018\u0010\u0012\u001a\u0004\u0018\u00010\b2\u0006\u0010\u000e\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\u0010J\u0018\u0010\u0013\u001a\u0004\u0018\u00010\b2\u0006\u0010\u000e\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\u0010¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/types/TypeApproximator;", "Lorg/jetbrains/kotlin/types/AbstractTypeApproximator;", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "(Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;)V", "approximateDeclarationType", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "baseType", "Lorg/jetbrains/kotlin/types/KotlinType;", "local", "", "approximateTo", ModuleXmlParser.TYPE, "conf", "Lorg/jetbrains/kotlin/types/TypeApproximatorConfiguration;", "toSuperType", "approximateToSubType", "approximateToSuperType", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TypeApproximator extends AbstractTypeApproximator {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TypeApproximator(KotlinBuiltIns builtIns, LanguageVersionSettings languageVersionSettings) {
        super(new ClassicTypeSystemContextForCS(builtIns, KotlinTypeRefiner.Default.INSTANCE), languageVersionSettings);
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
    }

    public final UnwrappedType approximateDeclarationType(KotlinType baseType, boolean local) {
        Intrinsics.checkNotNullParameter(baseType, "baseType");
        if (!getLanguageVersionSettings().supportsFeature(LanguageFeature.NewInference)) {
            return baseType.unwrap();
        }
        TypeApproximatorConfiguration.AllFlexibleSameValue allFlexibleSameValue = local ? TypeApproximatorConfiguration.LocalDeclaration.INSTANCE : TypeApproximatorConfiguration.PublicDeclaration.SaveAnonymousTypes.INSTANCE;
        UnwrappedType unwrappedTypeUnwrap = local ? baseType.unwrap() : AlternativeTypeSubstitutionKt.substituteAlternativesInPublicType(baseType);
        UnwrappedType unwrappedTypeApproximateToSuperType = approximateToSuperType(unwrappedTypeUnwrap, (TypeApproximatorConfiguration) allFlexibleSameValue);
        return unwrappedTypeApproximateToSuperType == null ? unwrappedTypeUnwrap : unwrappedTypeApproximateToSuperType;
    }

    public final UnwrappedType approximateToSuperType(UnwrappedType type, TypeApproximatorConfiguration conf) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(conf, "conf");
        return (UnwrappedType) super.approximateToSuperType((KotlinTypeMarker) type, conf);
    }

    public final UnwrappedType approximateToSubType(UnwrappedType type, TypeApproximatorConfiguration conf) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(conf, "conf");
        return (UnwrappedType) super.approximateToSubType((KotlinTypeMarker) type, conf);
    }

    public final UnwrappedType approximateTo(UnwrappedType type, TypeApproximatorConfiguration conf, boolean toSuperType) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(conf, "conf");
        return toSuperType ? approximateToSuperType(type, conf) : approximateToSubType(type, conf);
    }
}
