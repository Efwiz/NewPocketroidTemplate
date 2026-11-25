package org.jetbrains.kotlin.codegen;

import com.reandroid.dex.sections.Marker;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: JvmBackendClassResolver.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001:\u0001\u0007J\u0016\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u0006H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/codegen/JvmBackendClassResolver;", "", "resolveToClassDescriptors", "", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", ModuleXmlParser.TYPE, "Lorg/jetbrains/org/objectweb/asm/Type;", "Dummy", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface JvmBackendClassResolver {
    List<ClassDescriptor> resolveToClassDescriptors(Type type);

    /* JADX INFO: compiled from: JvmBackendClassResolver.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0016¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/codegen/JvmBackendClassResolver$Dummy;", "Lorg/jetbrains/kotlin/codegen/JvmBackendClassResolver;", "()V", "resolveToClassDescriptors", "", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", ModuleXmlParser.TYPE, "Lorg/jetbrains/org/objectweb/asm/Type;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Dummy implements JvmBackendClassResolver {
        public static final Dummy INSTANCE = new Dummy();

        private Dummy() {
        }

        @Override // org.jetbrains.kotlin.codegen.JvmBackendClassResolver
        public List<ClassDescriptor> resolveToClassDescriptors(Type type) {
            Intrinsics.checkNotNullParameter(type, "type");
            return CollectionsKt.emptyList();
        }
    }
}
