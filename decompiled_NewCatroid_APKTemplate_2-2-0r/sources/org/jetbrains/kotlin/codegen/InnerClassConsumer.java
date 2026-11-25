package org.jetbrains.kotlin.codegen;

import com.reandroid.dex.sections.Marker;
import java.util.Collections;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.impl.ClassDescriptorImpl;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.storage.LockBasedStorageManager;

/* JADX INFO: compiled from: InnerClassConsumer.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u0000 \u00062\u00020\u0001:\u0001\u0006J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0007À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/codegen/InnerClassConsumer;", "", "addInnerClassInfoFromAnnotation", "", "classDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "Companion", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface InnerClassConsumer {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    void addInnerClassInfoFromAnnotation(ClassDescriptor classDescriptor);

    /* JADX INFO: compiled from: InnerClassConsumer.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/codegen/InnerClassConsumer$Companion;", "", "()V", "classForInnerClassRecord", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "descriptor", "defaultImpls", "", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }

        public final ClassDescriptor classForInnerClassRecord(ClassDescriptor descriptor, boolean defaultImpls) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            if (defaultImpls) {
                ClassDescriptor classDescriptor = descriptor;
                if (DescriptorUtils.isLocal(classDescriptor)) {
                    return null;
                }
                ClassDescriptorImpl classDescriptorImpl = new ClassDescriptorImpl(classDescriptor, Name.identifier(JvmAbi.DEFAULT_IMPLS_CLASS_NAME), Modality.FINAL, ClassKind.CLASS, Collections.emptyList(), SourceElement.NO_SOURCE, false, LockBasedStorageManager.NO_LOCKS);
                classDescriptorImpl.initialize(MemberScope.Empty.INSTANCE, SetsKt.emptySet(), null);
                return classDescriptorImpl;
            }
            if (DescriptorUtils.isTopLevelDeclaration(descriptor)) {
                return null;
            }
            return descriptor;
        }
    }
}
