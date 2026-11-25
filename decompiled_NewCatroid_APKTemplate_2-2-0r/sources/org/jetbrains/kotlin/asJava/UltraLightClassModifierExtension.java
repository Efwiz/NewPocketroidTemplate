package org.jetbrains.kotlin.asJava;

import java.util.List;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.asJava.classes.KtUltraLightClass;
import org.jetbrains.kotlin.asJava.elements.KtLightField;
import org.jetbrains.kotlin.asJava.elements.KtLightMethod;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.extensions.ProjectExtensionDescriptor;
import org.jetbrains.kotlin.psi.KtDeclaration;

/* JADX INFO: compiled from: UltraLightClassModifierExtension.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\bf\u0018\u0000 \u00142\u00020\u0001:\u0001\u0014J6\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u000e\u0010\u0006\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\b0\u00072\u0006\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fH\u0016J6\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u000e\u0010\u0006\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\b0\u00072\u0006\u0010\t\u001a\u00020\n2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\fH\u0016J(\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0004\u001a\u00020\u00052\u000e\u0010\u0006\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\b0\u00072\u0006\u0010\u0013\u001a\u00020\u0012H\u0016ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0015À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/asJava/UltraLightClassModifierExtension;", "", "interceptFieldsBuilding", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lkotlin/Lazy;", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "containingDeclaration", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightClass;", "fieldsList", "", "Lorg/jetbrains/kotlin/asJava/elements/KtLightField;", "interceptMethodsBuilding", "methodsList", "Lorg/jetbrains/kotlin/asJava/elements/KtLightMethod;", "interceptModalityBuilding", "", "modifier", "Companion", "light-classes"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface UltraLightClassModifierExtension {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    default void interceptFieldsBuilding(KtDeclaration declaration, Lazy<? extends DeclarationDescriptor> descriptor, KtUltraLightClass containingDeclaration, List<KtLightField> fieldsList) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(containingDeclaration, "containingDeclaration");
        Intrinsics.checkNotNullParameter(fieldsList, "fieldsList");
    }

    default void interceptMethodsBuilding(KtDeclaration declaration, Lazy<? extends DeclarationDescriptor> descriptor, KtUltraLightClass containingDeclaration, List<KtLightMethod> methodsList) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(containingDeclaration, "containingDeclaration");
        Intrinsics.checkNotNullParameter(methodsList, "methodsList");
    }

    default String interceptModalityBuilding(KtDeclaration declaration, Lazy<? extends DeclarationDescriptor> descriptor, String modifier) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(modifier, "modifier");
        return modifier;
    }

    /* JADX INFO: compiled from: UltraLightClassModifierExtension.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/asJava/UltraLightClassModifierExtension$Companion;", "Lorg/jetbrains/kotlin/extensions/ProjectExtensionDescriptor;", "Lorg/jetbrains/kotlin/asJava/UltraLightClassModifierExtension;", "()V", "light-classes"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion extends ProjectExtensionDescriptor<UltraLightClassModifierExtension> {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
            super("org.jetbrains.kotlin.ultraLightClassModifierExtension", UltraLightClassModifierExtension.class);
        }
    }
}
