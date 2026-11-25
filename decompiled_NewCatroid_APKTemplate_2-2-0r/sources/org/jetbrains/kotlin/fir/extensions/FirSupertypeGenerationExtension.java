package org.jetbrains.kotlin.fir.extensions;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirUserTypeRef;

/* JADX INFO: compiled from: FirSupertypeGenerationExtension.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0006\b&\u0018\u0000 \u00182\u00020\u0001:\u0004\u0018\u0019\u001a\u001bB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J-\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\u0006\u0010\u0011\u001a\u00020\u00122\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eH&R\u00020\u0010¢\u0006\u0002\u0010\u0014J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0012H&R\u0019\u0010\u0005\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\n8F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/fir/extensions/FirSupertypeGenerationExtension;", "Lorg/jetbrains/kotlin/fir/extensions/FirExtension;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;)V", "extensionType", "Lkotlin/reflect/KClass;", "getExtensionType", "()Lkotlin/reflect/KClass;", "name", "Lorg/jetbrains/kotlin/fir/extensions/FirExtensionPointName;", "getName", "()Lorg/jetbrains/kotlin/fir/extensions/FirExtensionPointName;", "computeAdditionalSupertypes", "", "Lorg/jetbrains/kotlin/fir/types/FirResolvedTypeRef;", "Lorg/jetbrains/kotlin/fir/extensions/FirSupertypeGenerationExtension$TypeResolveServiceContainer;", "classLikeDeclaration", "Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;", "resolvedSupertypes", "(Lorg/jetbrains/kotlin/fir/extensions/FirSupertypeGenerationExtension$TypeResolveServiceContainer;Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;Ljava/util/List;)Ljava/util/List;", "needTransformSupertypes", "", "declaration", "Companion", "Factory", "TypeResolveService", "TypeResolveServiceContainer", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirSupertypeGenerationExtension extends FirExtension {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final FirExtensionPointName NAME = new FirExtensionPointName("SupertypeGenerator");
    private final KClass<? extends FirExtension> extensionType;

    /* JADX INFO: compiled from: FirSupertypeGenerationExtension.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bæ\u0080\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0003À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/extensions/FirSupertypeGenerationExtension$Factory;", "Lorg/jetbrains/kotlin/fir/extensions/FirExtension$Factory;", "Lorg/jetbrains/kotlin/fir/extensions/FirSupertypeGenerationExtension;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface Factory extends FirExtension.Factory<FirSupertypeGenerationExtension> {
    }

    /* JADX INFO: compiled from: FirSupertypeGenerationExtension.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H&¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/extensions/FirSupertypeGenerationExtension$TypeResolveService;", "", "()V", "resolveUserType", "Lorg/jetbrains/kotlin/fir/types/FirResolvedTypeRef;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/fir/types/FirUserTypeRef;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static abstract class TypeResolveService {
        public abstract FirResolvedTypeRef resolveUserType(FirUserTypeRef type);
    }

    public abstract List<FirResolvedTypeRef> computeAdditionalSupertypes(TypeResolveServiceContainer typeResolveServiceContainer, FirClassLikeDeclaration firClassLikeDeclaration, List<? extends FirResolvedTypeRef> list);

    public abstract boolean needTransformSupertypes(FirClassLikeDeclaration declaration);

    /* JADX INFO: compiled from: FirSupertypeGenerationExtension.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/extensions/FirSupertypeGenerationExtension$Companion;", "", "()V", "NAME", "Lorg/jetbrains/kotlin/fir/extensions/FirExtensionPointName;", "getNAME", "()Lorg/jetbrains/kotlin/fir/extensions/FirExtensionPointName;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final FirExtensionPointName getNAME() {
            return FirSupertypeGenerationExtension.NAME;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirSupertypeGenerationExtension(FirSession session) {
        super(session);
        Intrinsics.checkNotNullParameter(session, "session");
        this.extensionType = Reflection.getOrCreateKotlinClass(FirSupertypeGenerationExtension.class);
    }

    @Override // org.jetbrains.kotlin.fir.extensions.FirExtension
    public final FirExtensionPointName getName() {
        return NAME;
    }

    @Override // org.jetbrains.kotlin.fir.extensions.FirExtension
    public final KClass<? extends FirExtension> getExtensionType() {
        return this.extensionType;
    }

    /* JADX INFO: compiled from: FirSupertypeGenerationExtension.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/extensions/FirSupertypeGenerationExtension$TypeResolveServiceContainer;", "", "typeResolver", "Lorg/jetbrains/kotlin/fir/extensions/FirSupertypeGenerationExtension$TypeResolveService;", "(Lorg/jetbrains/kotlin/fir/extensions/FirSupertypeGenerationExtension$TypeResolveService;)V", "getTypeResolver", "()Lorg/jetbrains/kotlin/fir/extensions/FirSupertypeGenerationExtension$TypeResolveService;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class TypeResolveServiceContainer {
        private final TypeResolveService typeResolver;

        public TypeResolveServiceContainer(TypeResolveService typeResolver) {
            Intrinsics.checkNotNullParameter(typeResolver, "typeResolver");
            this.typeResolver = typeResolver;
        }

        public final TypeResolveService getTypeResolver() {
            return this.typeResolver;
        }
    }
}
