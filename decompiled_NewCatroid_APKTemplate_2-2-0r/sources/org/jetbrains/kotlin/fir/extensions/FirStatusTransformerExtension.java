package org.jetbrains.kotlin.fir.extensions;

import androidx.core.app.NotificationCompat;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirBackingField;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus;
import org.jetbrains.kotlin.fir.declarations.FirEnumEntry;
import org.jetbrains.kotlin.fir.declarations.FirField;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.load.java.JvmAbi;

/* JADX INFO: compiled from: FirStatusTransformerExtension.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000x\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u0000 *2\u00020\u0001:\u0002*+B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H&J.\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u00152\f\u0010\u0016\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00172\u0006\u0010\u0018\u001a\u00020\u000eH\u0016J.\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0019\u001a\u00020\u001a2\f\u0010\u0016\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00172\u0006\u0010\u0018\u001a\u00020\u000eH\u0016J\u0018\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u000f\u001a\u00020\u0010H\u0014J.\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u001b\u001a\u00020\u001c2\f\u0010\u0016\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00172\u0006\u0010\u0018\u001a\u00020\u000eH\u0016J.\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u001d\u001a\u00020\u001e2\f\u0010\u0016\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00172\u0006\u0010\u0018\u001a\u00020\u000eH\u0016J.\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u001f\u001a\u00020 2\f\u0010\u0016\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00172\u0006\u0010\u0018\u001a\u00020\u000eH\u0016J8\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00122\u0006\u0010!\u001a\u00020\"2\f\u0010\u0016\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00172\b\u0010#\u001a\u0004\u0018\u00010 2\u0006\u0010\u0018\u001a\u00020\u000eH\u0016J.\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00122\u0006\u0010$\u001a\u00020%2\f\u0010\u0016\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00172\u0006\u0010\u0018\u001a\u00020\u000eH\u0016J.\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00122\u0006\u0010&\u001a\u00020'2\f\u0010\u0016\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00172\u0006\u0010\u0018\u001a\u00020\u000eH\u0016J.\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00122\u0006\u0010(\u001a\u00020)2\f\u0010\u0016\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00172\u0006\u0010\u0018\u001a\u00020\u000eH\u0016R\u0019\u0010\u0005\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\n8F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f¨\u0006,"}, d2 = {"Lorg/jetbrains/kotlin/fir/extensions/FirStatusTransformerExtension;", "Lorg/jetbrains/kotlin/fir/extensions/FirExtension;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;)V", "extensionType", "Lkotlin/reflect/KClass;", "getExtensionType", "()Lkotlin/reflect/KClass;", "name", "Lorg/jetbrains/kotlin/fir/extensions/FirExtensionPointName;", "getName", "()Lorg/jetbrains/kotlin/fir/extensions/FirExtensionPointName;", "needTransformStatus", "", "declaration", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "transformStatus", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", NotificationCompat.CATEGORY_STATUS, "backingField", "Lorg/jetbrains/kotlin/fir/declarations/FirBackingField;", "containingClass", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "isLocal", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;", "enumEntry", "Lorg/jetbrains/kotlin/fir/declarations/FirEnumEntry;", "field", "Lorg/jetbrains/kotlin/fir/declarations/FirField;", "property", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "propertyAccessor", "Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;", "containingProperty", "regularClass", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "function", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "typeAlias", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeAlias;", "Companion", "Factory", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirStatusTransformerExtension extends FirExtension {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final FirExtensionPointName NAME = new FirExtensionPointName("StatusTransformer");
    private final KClass<? extends FirExtension> extensionType;

    /* JADX INFO: compiled from: FirStatusTransformerExtension.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bæ\u0080\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0003À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/extensions/FirStatusTransformerExtension$Factory;", "Lorg/jetbrains/kotlin/fir/extensions/FirExtension$Factory;", "Lorg/jetbrains/kotlin/fir/extensions/FirStatusTransformerExtension;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface Factory extends FirExtension.Factory<FirStatusTransformerExtension> {
    }

    public abstract boolean needTransformStatus(FirDeclaration declaration);

    protected FirDeclarationStatus transformStatus(FirDeclarationStatus status, FirDeclaration declaration) {
        Intrinsics.checkNotNullParameter(status, "status");
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return status;
    }

    /* JADX INFO: compiled from: FirStatusTransformerExtension.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/extensions/FirStatusTransformerExtension$Companion;", "", "()V", "NAME", "Lorg/jetbrains/kotlin/fir/extensions/FirExtensionPointName;", "getNAME", "()Lorg/jetbrains/kotlin/fir/extensions/FirExtensionPointName;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final FirExtensionPointName getNAME() {
            return FirStatusTransformerExtension.NAME;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirStatusTransformerExtension(FirSession session) {
        super(session);
        Intrinsics.checkNotNullParameter(session, "session");
        this.extensionType = Reflection.getOrCreateKotlinClass(FirStatusTransformerExtension.class);
    }

    @Override // org.jetbrains.kotlin.fir.extensions.FirExtension
    public final FirExtensionPointName getName() {
        return NAME;
    }

    @Override // org.jetbrains.kotlin.fir.extensions.FirExtension
    public final KClass<? extends FirExtension> getExtensionType() {
        return this.extensionType;
    }

    public FirDeclarationStatus transformStatus(FirDeclarationStatus status, FirProperty property, FirClassLikeSymbol<?> containingClass, boolean isLocal) {
        Intrinsics.checkNotNullParameter(status, "status");
        Intrinsics.checkNotNullParameter(property, "property");
        return transformStatus(status, property);
    }

    public FirDeclarationStatus transformStatus(FirDeclarationStatus status, FirSimpleFunction function, FirClassLikeSymbol<?> containingClass, boolean isLocal) {
        Intrinsics.checkNotNullParameter(status, "status");
        Intrinsics.checkNotNullParameter(function, "function");
        return transformStatus(status, function);
    }

    public FirDeclarationStatus transformStatus(FirDeclarationStatus status, FirRegularClass regularClass, FirClassLikeSymbol<?> containingClass, boolean isLocal) {
        Intrinsics.checkNotNullParameter(status, "status");
        Intrinsics.checkNotNullParameter(regularClass, "regularClass");
        return transformStatus(status, regularClass);
    }

    public FirDeclarationStatus transformStatus(FirDeclarationStatus status, FirTypeAlias typeAlias, FirClassLikeSymbol<?> containingClass, boolean isLocal) {
        Intrinsics.checkNotNullParameter(status, "status");
        Intrinsics.checkNotNullParameter(typeAlias, "typeAlias");
        return transformStatus(status, typeAlias);
    }

    public FirDeclarationStatus transformStatus(FirDeclarationStatus status, FirPropertyAccessor propertyAccessor, FirClassLikeSymbol<?> containingClass, FirProperty containingProperty, boolean isLocal) {
        Intrinsics.checkNotNullParameter(status, "status");
        Intrinsics.checkNotNullParameter(propertyAccessor, "propertyAccessor");
        return transformStatus(status, propertyAccessor);
    }

    public FirDeclarationStatus transformStatus(FirDeclarationStatus status, FirConstructor constructor, FirClassLikeSymbol<?> containingClass, boolean isLocal) {
        Intrinsics.checkNotNullParameter(status, "status");
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        return transformStatus(status, constructor);
    }

    public FirDeclarationStatus transformStatus(FirDeclarationStatus status, FirField field, FirClassLikeSymbol<?> containingClass, boolean isLocal) {
        Intrinsics.checkNotNullParameter(status, "status");
        Intrinsics.checkNotNullParameter(field, "field");
        return transformStatus(status, field);
    }

    public FirDeclarationStatus transformStatus(FirDeclarationStatus status, FirBackingField backingField, FirClassLikeSymbol<?> containingClass, boolean isLocal) {
        Intrinsics.checkNotNullParameter(status, "status");
        Intrinsics.checkNotNullParameter(backingField, "backingField");
        return transformStatus(status, backingField);
    }

    public FirDeclarationStatus transformStatus(FirDeclarationStatus status, FirEnumEntry enumEntry, FirClassLikeSymbol<?> containingClass, boolean isLocal) {
        Intrinsics.checkNotNullParameter(status, "status");
        Intrinsics.checkNotNullParameter(enumEntry, "enumEntry");
        return transformStatus(status, enumEntry);
    }
}
