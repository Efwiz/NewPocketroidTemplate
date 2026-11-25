package org.jetbrains.kotlin.extensions;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.types.DefaultTypeAttributeTranslator;
import org.jetbrains.kotlin.types.TypeAttributeTranslator;
import org.jetbrains.kotlin.types.extensions.TypeAttributeTranslators;

/* JADX INFO: compiled from: TypeAttributeTranslatorExtension.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u0000 \u00022\u00020\u0001:\u0001\u0002ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0003À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/extensions/TypeAttributeTranslatorExtension;", "Lorg/jetbrains/kotlin/types/TypeAttributeTranslator;", "Companion", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface TypeAttributeTranslatorExtension extends TypeAttributeTranslator {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    /* JADX INFO: compiled from: TypeAttributeTranslatorExtension.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u000e\u0010\b\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\nR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/extensions/TypeAttributeTranslatorExtension$Companion;", "Lorg/jetbrains/kotlin/extensions/ProjectExtensionDescriptor;", "Lorg/jetbrains/kotlin/extensions/TypeAttributeTranslatorExtension;", "()V", "Default", "Lorg/jetbrains/kotlin/types/extensions/TypeAttributeTranslators;", "getDefault", "()Lorg/jetbrains/kotlin/types/extensions/TypeAttributeTranslators;", "createTranslators", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion extends ProjectExtensionDescriptor<TypeAttributeTranslatorExtension> {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();
        private static final TypeAttributeTranslators Default = new TypeAttributeTranslators(CollectionsKt.listOf(DefaultTypeAttributeTranslator.INSTANCE));

        private Companion() {
            super("org.jetbrains.kotlin.extensions.typeAttributeTranslatorExtension", TypeAttributeTranslatorExtension.class);
        }

        public final TypeAttributeTranslators getDefault() {
            return Default;
        }

        public final TypeAttributeTranslators createTranslators(Project project) {
            Intrinsics.checkNotNullParameter(project, "project");
            return new TypeAttributeTranslators(CollectionsKt.plus((Collection<? extends DefaultTypeAttributeTranslator>) getInstances(project), DefaultTypeAttributeTranslator.INSTANCE));
        }
    }
}
