package org.jetbrains.kotlin.js.translate.context;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.MemberDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.DescriptorUtils;

/* JADX INFO: compiled from: UsageTracker.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000&\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\u001a\u0014\u0010\u0002\u001a\u0004\u0018\u00010\u0003*\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006\u001a\f\u0010\u0007\u001a\u00020\u0001*\u00020\bH\u0002\u001a\n\u0010\t\u001a\u00020\n*\u00020\u0004\u001a\u0012\u0010\u000b\u001a\u00020\n*\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082D¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"CAPTURED_RECEIVER_NAME_PREFIX", "", "getNameForCapturedDescriptor", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "Lorg/jetbrains/kotlin/js/translate/context/UsageTracker;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "getNameForCapturedReceiver", "Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "hasCapturedExceptContaining", "", "isCaptured", "js.translator"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class UsageTrackerKt {
    private static final String CAPTURED_RECEIVER_NAME_PREFIX = "this$";

    public static final JsName getNameForCapturedDescriptor(UsageTracker usageTracker, DeclarationDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(usageTracker, "<this>");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return usageTracker.getCapturedDescriptorToJsName().get(descriptor);
    }

    public static final boolean hasCapturedExceptContaining(UsageTracker usageTracker) {
        Intrinsics.checkNotNullParameter(usageTracker, "<this>");
        return !(usageTracker.getCapturedDescriptorToJsName().isEmpty() || (usageTracker.getCapturedDescriptorToJsName().size() == 1 && usageTracker.getCapturedDescriptorToJsName().containsKey(usageTracker.getContainingDescriptor())));
    }

    public static final boolean isCaptured(UsageTracker usageTracker, DeclarationDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(usageTracker, "<this>");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return usageTracker.getCapturedDescriptorToJsName().containsKey(descriptor);
    }

    static /* synthetic */ String getNameForCapturedReceiver$getNameForCapturedDescriptor$default(DeclarationDescriptor declarationDescriptor, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            str = "";
        }
        return getNameForCapturedReceiver$getNameForCapturedDescriptor(declarationDescriptor, str);
    }

    private static final String getNameForCapturedReceiver$getNameForCapturedDescriptor(DeclarationDescriptor declarationDescriptor, String str) {
        String strAsString;
        Name name2 = declarationDescriptor.getShortName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        if (name2.isSpecial()) {
            strAsString = "";
        } else {
            strAsString = name2.asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        }
        return CAPTURED_RECEIVER_NAME_PREFIX + strAsString + str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String getNameForCapturedReceiver(ReceiverParameterDescriptor receiverParameterDescriptor) {
        DeclarationDescriptor containingDeclaration = receiverParameterDescriptor.getContainingDeclaration();
        Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
        boolean z = containingDeclaration instanceof MemberDescriptor;
        if (DescriptorUtils.isCompanionObject(containingDeclaration)) {
            DeclarationDescriptor containingDeclaration2 = containingDeclaration.getContainingDeclaration();
            Intrinsics.checkNotNull(containingDeclaration2);
            return getNameForCapturedReceiver$getNameForCapturedDescriptor(containingDeclaration2, "$");
        }
        return getNameForCapturedReceiver$getNameForCapturedDescriptor$default(containingDeclaration, null, 1, null);
    }
}
