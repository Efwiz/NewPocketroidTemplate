package org.jetbrains.kotlin.util;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;

/* JADX INFO: compiled from: ModuleVisibilityHelper.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001:\u0001\u0007J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H&¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/util/ModuleVisibilityHelper;", "", "isInFriendModule", "", "what", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", TypedValues.TransitionType.S_FROM, "EMPTY", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ModuleVisibilityHelper {
    boolean isInFriendModule(DeclarationDescriptor what, DeclarationDescriptor from);

    /* JADX INFO: compiled from: ModuleVisibilityHelper.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0016¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/util/ModuleVisibilityHelper$EMPTY;", "Lorg/jetbrains/kotlin/util/ModuleVisibilityHelper;", "()V", "isInFriendModule", "", "what", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", TypedValues.TransitionType.S_FROM, "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class EMPTY implements ModuleVisibilityHelper {
        public static final EMPTY INSTANCE = new EMPTY();

        @Override // org.jetbrains.kotlin.util.ModuleVisibilityHelper
        public boolean isInFriendModule(DeclarationDescriptor what, DeclarationDescriptor from) {
            Intrinsics.checkNotNullParameter(what, "what");
            Intrinsics.checkNotNullParameter(from, "from");
            return true;
        }

        private EMPTY() {
        }
    }
}
