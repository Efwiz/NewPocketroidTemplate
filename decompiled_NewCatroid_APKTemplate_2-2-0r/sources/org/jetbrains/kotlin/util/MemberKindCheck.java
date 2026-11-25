package org.jetbrains.kotlin.util;

import com.google.android.gms.cast.MediaTrack;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;

/* JADX INFO: compiled from: modifierChecks.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0002\u0007\bB\u000f\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u0082\u0001\u0002\t\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/util/MemberKindCheck;", "Lorg/jetbrains/kotlin/util/Check;", MediaTrack.ROLE_DESCRIPTION, "", "(Ljava/lang/String;)V", "getDescription", "()Ljava/lang/String;", "Member", "MemberOrExtension", "Lorg/jetbrains/kotlin/util/MemberKindCheck$Member;", "Lorg/jetbrains/kotlin/util/MemberKindCheck$MemberOrExtension;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class MemberKindCheck implements Check {
    private final String description;

    public /* synthetic */ MemberKindCheck(String str, DefaultConstructorMarker defaultConstructorMarker) {
        this(str);
    }

    /* JADX INFO: compiled from: modifierChecks.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/util/MemberKindCheck$MemberOrExtension;", "Lorg/jetbrains/kotlin/util/MemberKindCheck;", "()V", "check", "", "functionDescriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class MemberOrExtension extends MemberKindCheck {
        public static final MemberOrExtension INSTANCE = new MemberOrExtension();

        private MemberOrExtension() {
            super("must be a member or an extension function", null);
        }

        @Override // org.jetbrains.kotlin.util.Check
        public boolean check(FunctionDescriptor functionDescriptor) {
            Intrinsics.checkNotNullParameter(functionDescriptor, "functionDescriptor");
            return (functionDescriptor.mo6753getDispatchReceiverParameter() == null && functionDescriptor.getExtensionReceiverParameter() == null) ? false : true;
        }
    }

    private MemberKindCheck(String str) {
        this.description = str;
    }

    @Override // org.jetbrains.kotlin.util.Check
    public String getDescription() {
        return this.description;
    }

    @Override // org.jetbrains.kotlin.util.Check
    public String invoke(FunctionDescriptor functionDescriptor) {
        return Check.DefaultImpls.invoke(this, functionDescriptor);
    }

    /* JADX INFO: compiled from: modifierChecks.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/util/MemberKindCheck$Member;", "Lorg/jetbrains/kotlin/util/MemberKindCheck;", "()V", "check", "", "functionDescriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Member extends MemberKindCheck {
        public static final Member INSTANCE = new Member();

        private Member() {
            super("must be a member function", null);
        }

        @Override // org.jetbrains.kotlin.util.Check
        public boolean check(FunctionDescriptor functionDescriptor) {
            Intrinsics.checkNotNullParameter(functionDescriptor, "functionDescriptor");
            return functionDescriptor.mo6753getDispatchReceiverParameter() != null;
        }
    }
}
