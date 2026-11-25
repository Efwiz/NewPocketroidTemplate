package org.jetbrains.kotlin.fir.extensions;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.types.ConeAttribute;

/* JADX INFO: compiled from: FirTypeAttributeExtension.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b&\u0018\u0000 \u00132\u00020\u0001:\u0002\u0013\u0014B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0016\u0010\r\u001a\u0004\u0018\u00010\u000e2\n\u0010\u000f\u001a\u0006\u0012\u0002\b\u00030\u0010H&J\u0016\u0010\u0011\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00102\u0006\u0010\u0012\u001a\u00020\u000eH&R\u0019\u0010\u0005\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00010\u00068F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\n8F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/fir/extensions/FirTypeAttributeExtension;", "Lorg/jetbrains/kotlin/fir/extensions/FirExtension;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;)V", "extensionType", "Lkotlin/reflect/KClass;", "getExtensionType", "()Lkotlin/reflect/KClass;", "name", "Lorg/jetbrains/kotlin/fir/extensions/FirExtensionPointName;", "getName", "()Lorg/jetbrains/kotlin/fir/extensions/FirExtensionPointName;", "convertAttributeToAnnotation", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "attribute", "Lorg/jetbrains/kotlin/fir/types/ConeAttribute;", "extractAttributeFromAnnotation", "annotation", "Companion", "Factory", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirTypeAttributeExtension extends FirExtension {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final FirExtensionPointName NAME = new FirExtensionPointName("AdditionalTypeAttributeExtension");

    /* JADX INFO: compiled from: FirTypeAttributeExtension.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bæ\u0080\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0003À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/extensions/FirTypeAttributeExtension$Factory;", "Lorg/jetbrains/kotlin/fir/extensions/FirExtension$Factory;", "Lorg/jetbrains/kotlin/fir/extensions/FirTypeAttributeExtension;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface Factory extends FirExtension.Factory<FirTypeAttributeExtension> {
    }

    public abstract FirAnnotation convertAttributeToAnnotation(ConeAttribute<?> attribute);

    public abstract ConeAttribute<?> extractAttributeFromAnnotation(FirAnnotation annotation);

    @Override // org.jetbrains.kotlin.fir.extensions.FirExtension
    public final KClass<? extends FirExtension> getExtensionType() {
        return Reflection.getOrCreateKotlinClass(FirTypeAttributeExtension.class);
    }

    /* JADX INFO: compiled from: FirTypeAttributeExtension.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/extensions/FirTypeAttributeExtension$Companion;", "", "()V", "NAME", "Lorg/jetbrains/kotlin/fir/extensions/FirExtensionPointName;", "getNAME", "()Lorg/jetbrains/kotlin/fir/extensions/FirExtensionPointName;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final FirExtensionPointName getNAME() {
            return FirTypeAttributeExtension.NAME;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirTypeAttributeExtension(FirSession session) {
        super(session);
        Intrinsics.checkNotNullParameter(session, "session");
    }

    @Override // org.jetbrains.kotlin.fir.extensions.FirExtension
    public final FirExtensionPointName getName() {
        return NAME;
    }
}
