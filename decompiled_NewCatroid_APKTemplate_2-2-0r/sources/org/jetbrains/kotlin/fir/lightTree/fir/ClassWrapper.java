package org.jetbrains.kotlin.fir.lightTree.fir;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.fir.declarations.builder.FirClassBuilder;
import org.jetbrains.kotlin.fir.lightTree.fir.modifier.Modifier;
import org.jetbrains.kotlin.fir.types.FirTypeRef;

/* JADX INFO: compiled from: ClassWrapper.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001BK\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\t\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\f\u0012\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f¢\u0006\u0002\u0010\u0011J\u0006\u0010\u001c\u001a\u00020\u001dJ\u0006\u0010\u001e\u001a\u00020\tJ\u0006\u0010\u001f\u001a\u00020\tJ\u0006\u0010 \u001a\u00020\tJ\u0006\u0010!\u001a\u00020\tJ\u0006\u0010\"\u001a\u00020\tJ\b\u0010#\u001a\u00020\tH\u0002J\u0006\u0010$\u001a\u00020\tR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\r\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0015R\u0011\u0010\n\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001aR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006%"}, d2 = {"Lorg/jetbrains/kotlin/fir/lightTree/fir/ClassWrapper;", "", "modifiers", "Lorg/jetbrains/kotlin/fir/lightTree/fir/modifier/Modifier;", "classKind", "Lorg/jetbrains/kotlin/descriptors/ClassKind;", "classBuilder", "Lorg/jetbrains/kotlin/fir/declarations/builder/FirClassBuilder;", "hasSecondaryConstructor", "", "hasDefaultConstructor", "delegatedSelfTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "delegatedSuperTypeRef", "delegatedSuperCalls", "", "Lorg/jetbrains/kotlin/fir/lightTree/fir/DelegatedConstructorWrapper;", "(Lorg/jetbrains/kotlin/fir/lightTree/fir/modifier/Modifier;Lorg/jetbrains/kotlin/descriptors/ClassKind;Lorg/jetbrains/kotlin/fir/declarations/builder/FirClassBuilder;ZZLorg/jetbrains/kotlin/fir/types/FirTypeRef;Lorg/jetbrains/kotlin/fir/types/FirTypeRef;Ljava/util/List;)V", "getClassBuilder", "()Lorg/jetbrains/kotlin/fir/declarations/builder/FirClassBuilder;", "getDelegatedSelfTypeRef", "()Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getDelegatedSuperCalls", "()Ljava/util/List;", "getDelegatedSuperTypeRef", "getHasDefaultConstructor", "()Z", "getHasSecondaryConstructor", "defaultConstructorVisibility", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "hasExpect", "isEnum", "isEnumEntry", "isInner", "isInterface", "isObject", "isSealed", "light-tree2fir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ClassWrapper {
    private final FirClassBuilder classBuilder;
    private final ClassKind classKind;
    private final FirTypeRef delegatedSelfTypeRef;
    private final List<DelegatedConstructorWrapper> delegatedSuperCalls;
    private final FirTypeRef delegatedSuperTypeRef;
    private final boolean hasDefaultConstructor;
    private final boolean hasSecondaryConstructor;
    private final Modifier modifiers;

    public ClassWrapper(Modifier modifiers, ClassKind classKind, FirClassBuilder classBuilder, boolean z, boolean z2, FirTypeRef delegatedSelfTypeRef, FirTypeRef delegatedSuperTypeRef, List<DelegatedConstructorWrapper> delegatedSuperCalls) {
        Intrinsics.checkNotNullParameter(modifiers, "modifiers");
        Intrinsics.checkNotNullParameter(classKind, "classKind");
        Intrinsics.checkNotNullParameter(classBuilder, "classBuilder");
        Intrinsics.checkNotNullParameter(delegatedSelfTypeRef, "delegatedSelfTypeRef");
        Intrinsics.checkNotNullParameter(delegatedSuperTypeRef, "delegatedSuperTypeRef");
        Intrinsics.checkNotNullParameter(delegatedSuperCalls, "delegatedSuperCalls");
        this.modifiers = modifiers;
        this.classKind = classKind;
        this.classBuilder = classBuilder;
        this.hasSecondaryConstructor = z;
        this.hasDefaultConstructor = z2;
        this.delegatedSelfTypeRef = delegatedSelfTypeRef;
        this.delegatedSuperTypeRef = delegatedSuperTypeRef;
        this.delegatedSuperCalls = delegatedSuperCalls;
    }

    public final FirClassBuilder getClassBuilder() {
        return this.classBuilder;
    }

    public final boolean getHasSecondaryConstructor() {
        return this.hasSecondaryConstructor;
    }

    public final boolean getHasDefaultConstructor() {
        return this.hasDefaultConstructor;
    }

    public final FirTypeRef getDelegatedSelfTypeRef() {
        return this.delegatedSelfTypeRef;
    }

    public final FirTypeRef getDelegatedSuperTypeRef() {
        return this.delegatedSuperTypeRef;
    }

    public final List<DelegatedConstructorWrapper> getDelegatedSuperCalls() {
        return this.delegatedSuperCalls;
    }

    public final boolean isEnumEntry() {
        return this.classKind == ClassKind.ENUM_ENTRY;
    }

    private final boolean isObject() {
        return this.classKind == ClassKind.OBJECT;
    }

    public final boolean isSealed() {
        return this.modifiers.hasModality(Modality.SEALED);
    }

    public final boolean isEnum() {
        return this.modifiers.isEnum();
    }

    public final boolean isInterface() {
        return this.classKind == ClassKind.INTERFACE;
    }

    public final boolean isInner() {
        return this.modifiers.isInner();
    }

    public final boolean hasExpect() {
        return this.modifiers.hasExpect();
    }

    public final Visibility defaultConstructorVisibility() {
        if (isObject() || isEnum() || isEnumEntry()) {
            return Visibilities.Private.INSTANCE;
        }
        return isSealed() ? Visibilities.Protected.INSTANCE : Visibilities.Unknown.INSTANCE;
    }
}
