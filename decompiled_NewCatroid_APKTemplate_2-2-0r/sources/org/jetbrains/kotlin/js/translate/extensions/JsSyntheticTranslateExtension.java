package org.jetbrains.kotlin.js.translate.extensions;

import kotlin.Metadata;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.extensions.ProjectExtensionDescriptor;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.declaration.DeclarationBodyVisitor;
import org.jetbrains.kotlin.psi.KtPureClassOrObject;

/* JADX INFO: compiled from: JsSyntheticTranslateExtension.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u0000 \f2\u00020\u0001:\u0001\fJ(\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\rÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/extensions/JsSyntheticTranslateExtension;", "", "generateClassSyntheticParts", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtPureClassOrObject;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "translator", "Lorg/jetbrains/kotlin/js/translate/declaration/DeclarationBodyVisitor;", "context", "Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", "Companion", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface JsSyntheticTranslateExtension {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    void generateClassSyntheticParts(KtPureClassOrObject declaration, ClassDescriptor descriptor, DeclarationBodyVisitor translator, TranslationContext context);

    /* JADX INFO: compiled from: JsSyntheticTranslateExtension.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/extensions/JsSyntheticTranslateExtension$Companion;", "Lorg/jetbrains/kotlin/extensions/ProjectExtensionDescriptor;", "Lorg/jetbrains/kotlin/js/translate/extensions/JsSyntheticTranslateExtension;", "()V", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion extends ProjectExtensionDescriptor<JsSyntheticTranslateExtension> {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
            super("org.jetbrains.kotlin.jsSyntheticTranslateExtension", JsSyntheticTranslateExtension.class);
        }
    }
}
