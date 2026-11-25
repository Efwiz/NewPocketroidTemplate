package org.jetbrains.kotlin.ir.util;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: NameProvider.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001:\u0001\u0006J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/NameProvider;", "", "nameForDeclaration", "Lorg/jetbrains/kotlin/name/Name;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "DEFAULT", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface NameProvider {
    Name nameForDeclaration(DeclarationDescriptor descriptor);

    /* JADX INFO: compiled from: NameProvider.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/NameProvider$DEFAULT;", "Lorg/jetbrains/kotlin/ir/util/NameProvider;", "()V", "nameForDeclaration", "Lorg/jetbrains/kotlin/name/Name;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class DEFAULT implements NameProvider {
        public static final DEFAULT INSTANCE = new DEFAULT();

        private DEFAULT() {
        }

        @Override // org.jetbrains.kotlin.ir.util.NameProvider
        public Name nameForDeclaration(DeclarationDescriptor descriptor) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            Name name2 = descriptor.getName();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            return name2;
        }
    }
}
