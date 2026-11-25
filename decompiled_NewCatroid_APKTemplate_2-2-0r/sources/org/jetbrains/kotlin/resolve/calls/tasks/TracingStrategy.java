package org.jetbrains.kotlin.resolve.calls.tasks;

import java.util.Collection;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.calls.context.ResolutionContext;
import org.jetbrains.kotlin.resolve.calls.inference.InferenceErrorData;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: loaded from: classes6.dex */
public interface TracingStrategy {
    public static final TracingStrategy EMPTY = new TracingStrategy() { // from class: org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy.1
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            switch (i) {
                case 1:
                    objArr[0] = "call";
                    break;
                case 2:
                case 4:
                case 6:
                case 7:
                case 9:
                case 11:
                case 13:
                case 17:
                case 18:
                case 20:
                case 22:
                case 24:
                case 26:
                case 28:
                case 30:
                case 31:
                case 32:
                case 35:
                case 37:
                default:
                    objArr[0] = "trace";
                    break;
                case 3:
                case 5:
                    objArr[0] = "resolvedCall";
                    break;
                case 8:
                case 10:
                    objArr[0] = "candidates";
                    break;
                case 12:
                    objArr[0] = "expectedReceiver";
                    break;
                case 14:
                    objArr[0] = "receiverParameter";
                    break;
                case 15:
                    objArr[0] = "receiverArgument";
                    break;
                case 16:
                    objArr[0] = "c";
                    break;
                case 19:
                    objArr[0] = "valueParameter";
                    break;
                case 21:
                case 38:
                    objArr[0] = "descriptor";
                    break;
                case 23:
                    objArr[0] = "resolvedCalls";
                    break;
                case 25:
                case 27:
                    objArr[0] = "descriptors";
                    break;
                case 29:
                    objArr[0] = "languageVersionSettings";
                    break;
                case 33:
                    objArr[0] = "classDescriptor";
                    break;
                case 34:
                    objArr[0] = "explicitReceiverKind";
                    break;
                case 36:
                    objArr[0] = ModuleXmlParser.TYPE;
                    break;
                case 39:
                    objArr[0] = "context";
                    break;
                case 40:
                    objArr[0] = "inferenceErrorData";
                    break;
            }
            objArr[1] = "org/jetbrains/kotlin/resolve/calls/tasks/TracingStrategy$1";
            switch (i) {
                case 2:
                case 3:
                    objArr[2] = "bindReference";
                    break;
                case 4:
                case 5:
                    objArr[2] = "bindResolvedCall";
                    break;
                case 6:
                    objArr[2] = "unresolvedReference";
                    break;
                case 7:
                case 8:
                    objArr[2] = "unresolvedReferenceWrongReceiver";
                    break;
                case 9:
                case 10:
                    objArr[2] = "recordAmbiguity";
                    break;
                case 11:
                case 12:
                    objArr[2] = "missingReceiver";
                    break;
                case 13:
                case 14:
                case 15:
                case 16:
                    objArr[2] = "wrongReceiverType";
                    break;
                case 17:
                    objArr[2] = "noReceiverAllowed";
                    break;
                case 18:
                case 19:
                    objArr[2] = "noValueForParameter";
                    break;
                case 20:
                case 21:
                    objArr[2] = "wrongNumberOfTypeArguments";
                    break;
                case 22:
                case 23:
                    objArr[2] = "ambiguity";
                    break;
                case 24:
                case 25:
                    objArr[2] = "noneApplicable";
                    break;
                case 26:
                case 27:
                    objArr[2] = "cannotCompleteResolve";
                    break;
                case 28:
                case 29:
                    objArr[2] = "recursiveType";
                    break;
                case 30:
                    objArr[2] = "instantiationOfAbstractClass";
                    break;
                case 31:
                    objArr[2] = "abstractSuperCall";
                    break;
                case 32:
                case 33:
                case 34:
                    objArr[2] = "nestedClassAccessViaInstanceReference";
                    break;
                case 35:
                case 36:
                    objArr[2] = "unsafeCall";
                    break;
                case 37:
                case 38:
                    objArr[2] = "invisibleMember";
                    break;
                case 39:
                case 40:
                    objArr[2] = "typeInferenceFailed";
                    break;
                default:
                    objArr[2] = "bindCall";
                    break;
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public void abstractSuperCall(BindingTrace bindingTrace) {
            if (bindingTrace == null) {
                $$$reportNull$$$0(31);
            }
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public <D extends CallableDescriptor> void ambiguity(BindingTrace bindingTrace, Collection<? extends ResolvedCall<D>> collection) {
            if (bindingTrace == null) {
                $$$reportNull$$$0(22);
            }
            if (collection == null) {
                $$$reportNull$$$0(23);
            }
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public void bindCall(BindingTrace bindingTrace, Call call) {
            if (bindingTrace == null) {
                $$$reportNull$$$0(0);
            }
            if (call == null) {
                $$$reportNull$$$0(1);
            }
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public <D extends CallableDescriptor> void bindReference(BindingTrace bindingTrace, ResolvedCall<D> resolvedCall) {
            if (bindingTrace == null) {
                $$$reportNull$$$0(2);
            }
            if (resolvedCall == null) {
                $$$reportNull$$$0(3);
            }
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public <D extends CallableDescriptor> void bindResolvedCall(BindingTrace bindingTrace, ResolvedCall<D> resolvedCall) {
            if (bindingTrace == null) {
                $$$reportNull$$$0(4);
            }
            if (resolvedCall == null) {
                $$$reportNull$$$0(5);
            }
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public <D extends CallableDescriptor> void cannotCompleteResolve(BindingTrace bindingTrace, Collection<? extends ResolvedCall<D>> collection) {
            if (bindingTrace == null) {
                $$$reportNull$$$0(26);
            }
            if (collection == null) {
                $$$reportNull$$$0(27);
            }
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public void instantiationOfAbstractClass(BindingTrace bindingTrace) {
            if (bindingTrace == null) {
                $$$reportNull$$$0(30);
            }
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public void invisibleMember(BindingTrace bindingTrace, DeclarationDescriptorWithVisibility declarationDescriptorWithVisibility) {
            if (bindingTrace == null) {
                $$$reportNull$$$0(37);
            }
            if (declarationDescriptorWithVisibility == null) {
                $$$reportNull$$$0(38);
            }
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public void missingReceiver(BindingTrace bindingTrace, ReceiverParameterDescriptor receiverParameterDescriptor) {
            if (bindingTrace == null) {
                $$$reportNull$$$0(11);
            }
            if (receiverParameterDescriptor == null) {
                $$$reportNull$$$0(12);
            }
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public void nestedClassAccessViaInstanceReference(BindingTrace bindingTrace, ClassDescriptor classDescriptor, ExplicitReceiverKind explicitReceiverKind) {
            if (bindingTrace == null) {
                $$$reportNull$$$0(32);
            }
            if (classDescriptor == null) {
                $$$reportNull$$$0(33);
            }
            if (explicitReceiverKind == null) {
                $$$reportNull$$$0(34);
            }
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public void noReceiverAllowed(BindingTrace bindingTrace) {
            if (bindingTrace == null) {
                $$$reportNull$$$0(17);
            }
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public void noValueForParameter(BindingTrace bindingTrace, ValueParameterDescriptor valueParameterDescriptor) {
            if (bindingTrace == null) {
                $$$reportNull$$$0(18);
            }
            if (valueParameterDescriptor == null) {
                $$$reportNull$$$0(19);
            }
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public <D extends CallableDescriptor> void noneApplicable(BindingTrace bindingTrace, Collection<? extends ResolvedCall<D>> collection) {
            if (bindingTrace == null) {
                $$$reportNull$$$0(24);
            }
            if (collection == null) {
                $$$reportNull$$$0(25);
            }
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public <D extends CallableDescriptor> void recordAmbiguity(BindingTrace bindingTrace, Collection<? extends ResolvedCall<D>> collection) {
            if (bindingTrace == null) {
                $$$reportNull$$$0(9);
            }
            if (collection == null) {
                $$$reportNull$$$0(10);
            }
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public void recursiveType(BindingTrace bindingTrace, LanguageVersionSettings languageVersionSettings, boolean z) {
            if (bindingTrace == null) {
                $$$reportNull$$$0(28);
            }
            if (languageVersionSettings == null) {
                $$$reportNull$$$0(29);
            }
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public void typeInferenceFailed(ResolutionContext<?> resolutionContext, InferenceErrorData inferenceErrorData) {
            if (resolutionContext == null) {
                $$$reportNull$$$0(39);
            }
            if (inferenceErrorData == null) {
                $$$reportNull$$$0(40);
            }
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public void unresolvedReference(BindingTrace bindingTrace) {
            if (bindingTrace == null) {
                $$$reportNull$$$0(6);
            }
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public <D extends CallableDescriptor> void unresolvedReferenceWrongReceiver(BindingTrace bindingTrace, Collection<? extends ResolvedCall<D>> collection) {
            if (bindingTrace == null) {
                $$$reportNull$$$0(7);
            }
            if (collection == null) {
                $$$reportNull$$$0(8);
            }
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public void unsafeCall(BindingTrace bindingTrace, KotlinType kotlinType, boolean z) {
            if (bindingTrace == null) {
                $$$reportNull$$$0(35);
            }
            if (kotlinType == null) {
                $$$reportNull$$$0(36);
            }
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public void wrongNumberOfTypeArguments(BindingTrace bindingTrace, int i, CallableDescriptor callableDescriptor) {
            if (bindingTrace == null) {
                $$$reportNull$$$0(20);
            }
            if (callableDescriptor == null) {
                $$$reportNull$$$0(21);
            }
        }

        @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
        public void wrongReceiverType(BindingTrace bindingTrace, ReceiverParameterDescriptor receiverParameterDescriptor, ReceiverValue receiverValue, ResolutionContext<?> resolutionContext) {
            if (bindingTrace == null) {
                $$$reportNull$$$0(13);
            }
            if (receiverParameterDescriptor == null) {
                $$$reportNull$$$0(14);
            }
            if (receiverValue == null) {
                $$$reportNull$$$0(15);
            }
            if (resolutionContext == null) {
                $$$reportNull$$$0(16);
            }
        }
    };

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "trace", "org/jetbrains/kotlin/resolve/calls/tasks/TracingStrategy", "abstractSuperCallWarning"));
    }

    void abstractSuperCall(BindingTrace bindingTrace);

    <D extends CallableDescriptor> void ambiguity(BindingTrace bindingTrace, Collection<? extends ResolvedCall<D>> collection);

    void bindCall(BindingTrace bindingTrace, Call call);

    <D extends CallableDescriptor> void bindReference(BindingTrace bindingTrace, ResolvedCall<D> resolvedCall);

    <D extends CallableDescriptor> void bindResolvedCall(BindingTrace bindingTrace, ResolvedCall<D> resolvedCall);

    <D extends CallableDescriptor> void cannotCompleteResolve(BindingTrace bindingTrace, Collection<? extends ResolvedCall<D>> collection);

    void instantiationOfAbstractClass(BindingTrace bindingTrace);

    void invisibleMember(BindingTrace bindingTrace, DeclarationDescriptorWithVisibility declarationDescriptorWithVisibility);

    void missingReceiver(BindingTrace bindingTrace, ReceiverParameterDescriptor receiverParameterDescriptor);

    void nestedClassAccessViaInstanceReference(BindingTrace bindingTrace, ClassDescriptor classDescriptor, ExplicitReceiverKind explicitReceiverKind);

    void noReceiverAllowed(BindingTrace bindingTrace);

    void noValueForParameter(BindingTrace bindingTrace, ValueParameterDescriptor valueParameterDescriptor);

    <D extends CallableDescriptor> void noneApplicable(BindingTrace bindingTrace, Collection<? extends ResolvedCall<D>> collection);

    <D extends CallableDescriptor> void recordAmbiguity(BindingTrace bindingTrace, Collection<? extends ResolvedCall<D>> collection);

    void recursiveType(BindingTrace bindingTrace, LanguageVersionSettings languageVersionSettings, boolean z);

    void typeInferenceFailed(ResolutionContext<?> resolutionContext, InferenceErrorData inferenceErrorData);

    void unresolvedReference(BindingTrace bindingTrace);

    <D extends CallableDescriptor> void unresolvedReferenceWrongReceiver(BindingTrace bindingTrace, Collection<? extends ResolvedCall<D>> collection);

    void unsafeCall(BindingTrace bindingTrace, KotlinType kotlinType, boolean z);

    void wrongNumberOfTypeArguments(BindingTrace bindingTrace, int i, CallableDescriptor callableDescriptor);

    void wrongReceiverType(BindingTrace bindingTrace, ReceiverParameterDescriptor receiverParameterDescriptor, ReceiverValue receiverValue, ResolutionContext<?> resolutionContext);

    default void abstractSuperCallWarning(BindingTrace bindingTrace) {
        if (bindingTrace == null) {
            $$$reportNull$$$0(0);
        }
        abstractSuperCall(bindingTrace);
    }
}
