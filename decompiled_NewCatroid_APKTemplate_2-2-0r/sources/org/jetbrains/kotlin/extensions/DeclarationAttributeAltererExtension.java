package org.jetbrains.kotlin.extensions;

import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.psi.KtModifierListOwner;

/* JADX INFO: compiled from: DeclarationAttributeAltererExtension.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u0000 \u000f2\u00020\u0001:\u0001\u000fJ.\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u00072\b\u0010\b\u001a\u0004\u0018\u00010\u00072\u0006\u0010\t\u001a\u00020\u0003H\u0017J6\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u00072\b\u0010\b\u001a\u0004\u0018\u00010\u00072\u0006\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000eH\u0016ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0010À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/extensions/DeclarationAttributeAltererExtension;", "", "refineDeclarationModality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "modifierListOwner", "Lorg/jetbrains/kotlin/psi/KtModifierListOwner;", "declaration", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "containingDeclaration", "currentModality", "isImplicitModality", "", "shouldConvertFirstSAMParameterToReceiver", "function", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "Companion", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface DeclarationAttributeAltererExtension {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    default Modality refineDeclarationModality(KtModifierListOwner modifierListOwner, DeclarationDescriptor declaration, DeclarationDescriptor containingDeclaration, Modality currentModality, boolean isImplicitModality) {
        Intrinsics.checkNotNullParameter(modifierListOwner, "modifierListOwner");
        Intrinsics.checkNotNullParameter(currentModality, "currentModality");
        return null;
    }

    default boolean shouldConvertFirstSAMParameterToReceiver(FunctionDescriptor function) {
        Intrinsics.checkNotNullParameter(function, "function");
        return false;
    }

    /* JADX INFO: compiled from: DeclarationAttributeAltererExtension.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/extensions/DeclarationAttributeAltererExtension$Companion;", "Lorg/jetbrains/kotlin/extensions/ProjectExtensionDescriptor;", "Lorg/jetbrains/kotlin/extensions/DeclarationAttributeAltererExtension;", "()V", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion extends ProjectExtensionDescriptor<DeclarationAttributeAltererExtension> {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
            super("org.jetbrains.kotlin.declarationAttributeAltererExtension", DeclarationAttributeAltererExtension.class);
        }
    }

    @Deprecated(message = "Use refineDeclarationModality(modifierListOwner, declaration, containingDeclaration, currentModality, bindingContext, isImplicitModality)", replaceWith = @ReplaceWith(expression = "refineDeclarationModality(modifierListOwner, declaration, containingDeclaration, currentModality, bindingContext, false)", imports = {}))
    default Modality refineDeclarationModality(KtModifierListOwner modifierListOwner, DeclarationDescriptor declaration, DeclarationDescriptor containingDeclaration, Modality currentModality) {
        Intrinsics.checkNotNullParameter(modifierListOwner, "modifierListOwner");
        Intrinsics.checkNotNullParameter(currentModality, "currentModality");
        return refineDeclarationModality(modifierListOwner, declaration, containingDeclaration, currentModality, false);
    }
}
