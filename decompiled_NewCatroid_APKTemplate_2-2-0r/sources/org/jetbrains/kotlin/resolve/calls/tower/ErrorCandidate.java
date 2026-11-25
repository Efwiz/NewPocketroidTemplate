package org.jetbrains.kotlin.resolve.calls.tower;

import com.huawei.hms.mlplugin.asr.MLAsrCaptureConstants;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: ErrorCandidateFactory.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000*\n\b\u0000\u0010\u0001 \u0001*\u00020\u00022\u00020\u0003:\u0001\tB\u000f\b\u0004\u0012\u0006\u0010\u0004\u001a\u00028\u0000¢\u0006\u0002\u0010\u0005R\u0013\u0010\u0004\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\b\u001a\u0004\b\u0006\u0010\u0007\u0082\u0001\u0001\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/ErrorCandidate;", "D", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "", "descriptor", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;)V", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "Classifier", "Lorg/jetbrains/kotlin/resolve/calls/tower/ErrorCandidate$Classifier;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ErrorCandidate<D extends DeclarationDescriptor> {
    private final D descriptor;

    public /* synthetic */ ErrorCandidate(DeclarationDescriptor declarationDescriptor, DefaultConstructorMarker defaultConstructorMarker) {
        this(declarationDescriptor);
    }

    private ErrorCandidate(D d) {
        this.descriptor = d;
    }

    public final D getDescriptor() {
        return this.descriptor;
    }

    /* JADX INFO: compiled from: ErrorCandidateFactory.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/ErrorCandidate$Classifier;", "Lorg/jetbrains/kotlin/resolve/calls/tower/ErrorCandidate;", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", "classifierDescriptor", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/resolve/calls/tower/WrongResolutionToClassifier;", "(Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;Lorg/jetbrains/kotlin/resolve/calls/tower/WrongResolutionToClassifier;)V", MLAsrCaptureConstants.ASR_ERROR_MESSAGE, "", "getErrorMessage", "()Ljava/lang/String;", "getKind", "()Lorg/jetbrains/kotlin/resolve/calls/tower/WrongResolutionToClassifier;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Classifier extends ErrorCandidate<ClassifierDescriptor> {
        private final String errorMessage;
        private final WrongResolutionToClassifier kind;

        public final WrongResolutionToClassifier getKind() {
            return this.kind;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Classifier(ClassifierDescriptor classifierDescriptor, WrongResolutionToClassifier kind) {
            super(classifierDescriptor, null);
            Intrinsics.checkNotNullParameter(classifierDescriptor, "classifierDescriptor");
            Intrinsics.checkNotNullParameter(kind, "kind");
            this.kind = kind;
            Function1<Name, String> message = kind.getMessage();
            Name name2 = getDescriptor().getName();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            this.errorMessage = message.invoke(name2);
        }

        public final String getErrorMessage() {
            return this.errorMessage;
        }
    }
}
