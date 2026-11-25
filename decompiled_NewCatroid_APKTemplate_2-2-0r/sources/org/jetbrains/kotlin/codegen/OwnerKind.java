package org.jetbrains.kotlin.codegen;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;

/* JADX INFO: compiled from: OwnerKind.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\b\b\u0086\u0081\u0002\u0018\u0000 \b2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\bB\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/codegen/OwnerKind;", "", "(Ljava/lang/String;I)V", "PACKAGE", "IMPLEMENTATION", "DEFAULT_IMPLS", "ERASED_INLINE_CLASS", "PROPERTY_REFERENCE_SIGNATURE", "Companion", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum OwnerKind {
    PACKAGE,
    IMPLEMENTATION,
    DEFAULT_IMPLS,
    ERASED_INLINE_CLASS,
    PROPERTY_REFERENCE_SIGNATURE;

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    public static EnumEntries<OwnerKind> getEntries() {
        return $ENTRIES;
    }

    /* JADX INFO: compiled from: OwnerKind.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/codegen/OwnerKind$Companion;", "", "()V", "getMemberOwnerKind", "Lorg/jetbrains/kotlin/codegen/OwnerKind;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final OwnerKind getMemberOwnerKind(DeclarationDescriptor descriptor) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            if (descriptor instanceof PackageFragmentDescriptor) {
                return OwnerKind.PACKAGE;
            }
            if (descriptor instanceof ClassDescriptor) {
                return OwnerKind.IMPLEMENTATION;
            }
            throw new AssertionError("Unexpected declaration container: " + descriptor);
        }
    }
}
