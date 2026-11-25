package org.jetbrains.kotlin.backend.jvm.extensions;

import kotlin.Metadata;
import org.jetbrains.kotlin.extensions.ProjectExtensionDescriptor;
import org.jetbrains.kotlin.ir.declarations.IrClass;

/* JADX INFO: compiled from: ClassGeneratorExtension.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u0000 \u00072\u00020\u0001:\u0001\u0007J\u001a\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/extensions/ClassGeneratorExtension;", "", "generateClass", "Lorg/jetbrains/kotlin/backend/jvm/extensions/ClassGenerator;", "generator", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "Companion", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ClassGeneratorExtension {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    ClassGenerator generateClass(ClassGenerator generator, IrClass declaration);

    /* JADX INFO: compiled from: ClassGeneratorExtension.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/extensions/ClassGeneratorExtension$Companion;", "Lorg/jetbrains/kotlin/extensions/ProjectExtensionDescriptor;", "Lorg/jetbrains/kotlin/backend/jvm/extensions/ClassGeneratorExtension;", "()V", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion extends ProjectExtensionDescriptor<ClassGeneratorExtension> {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
            super("org.jetbrains.kotlin.classGeneratorExtension", ClassGeneratorExtension.class);
        }
    }
}
