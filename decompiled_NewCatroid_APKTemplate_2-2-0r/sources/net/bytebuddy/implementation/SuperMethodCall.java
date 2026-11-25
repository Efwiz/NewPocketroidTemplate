package net.bytebuddy.implementation;

import net.bytebuddy.build.HashCodeAndEqualsPlugin;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.dynamic.scaffold.InstrumentedType;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender;
import net.bytebuddy.implementation.bytecode.Removal;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.implementation.bytecode.member.MethodReturn;
import net.bytebuddy.implementation.bytecode.member.MethodVariableAccess;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.utility.nullability.MaybeNull;

/* JADX INFO: loaded from: classes12.dex */
public enum SuperMethodCall implements Implementation.Composable {
    INSTANCE;

    @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType.Prepareable
    public InstrumentedType prepare(InstrumentedType instrumentedType) {
        return instrumentedType;
    }

    @Override // net.bytebuddy.implementation.Implementation
    public ByteCodeAppender appender(Implementation.Target target) {
        return new Appender(target, Appender.TerminationHandler.RETURNING);
    }

    @Override // net.bytebuddy.implementation.Implementation.Composable
    public Implementation andThen(Implementation implementation) {
        return new Implementation.Compound(WithoutReturn.INSTANCE, implementation);
    }

    @Override // net.bytebuddy.implementation.Implementation.Composable
    public Implementation.Composable andThen(Implementation.Composable composable) {
        return new Implementation.Compound.Composable(WithoutReturn.INSTANCE, composable);
    }

    protected enum WithoutReturn implements Implementation {
        INSTANCE;

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType.Prepareable
        public InstrumentedType prepare(InstrumentedType instrumentedType) {
            return instrumentedType;
        }

        @Override // net.bytebuddy.implementation.Implementation
        public ByteCodeAppender appender(Implementation.Target target) {
            return new Appender(target, Appender.TerminationHandler.DROPPING);
        }
    }

    @HashCodeAndEqualsPlugin.Enhance
    protected static class Appender implements ByteCodeAppender {
        private final Implementation.Target implementationTarget;
        private final TerminationHandler terminationHandler;

        protected enum TerminationHandler {
            RETURNING { // from class: net.bytebuddy.implementation.SuperMethodCall.Appender.TerminationHandler.1
                @Override // net.bytebuddy.implementation.SuperMethodCall.Appender.TerminationHandler
                protected StackManipulation of(MethodDescription methodDescription) {
                    return MethodReturn.of(methodDescription.getReturnType());
                }
            },
            DROPPING { // from class: net.bytebuddy.implementation.SuperMethodCall.Appender.TerminationHandler.2
                @Override // net.bytebuddy.implementation.SuperMethodCall.Appender.TerminationHandler
                protected StackManipulation of(MethodDescription methodDescription) {
                    return Removal.of(methodDescription.getReturnType());
                }
            };

            protected abstract StackManipulation of(MethodDescription methodDescription);
        }

        public boolean equals(@MaybeNull Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Appender appender = (Appender) obj;
            return this.terminationHandler.equals(appender.terminationHandler) && this.implementationTarget.equals(appender.implementationTarget);
        }

        public int hashCode() {
            return (((getClass().hashCode() * 31) + this.implementationTarget.hashCode()) * 31) + this.terminationHandler.hashCode();
        }

        protected Appender(Implementation.Target target, TerminationHandler terminationHandler) {
            this.implementationTarget = target;
            this.terminationHandler = terminationHandler;
        }

        @Override // net.bytebuddy.implementation.bytecode.ByteCodeAppender
        public ByteCodeAppender.Size apply(MethodVisitor methodVisitor, Implementation.Context context, MethodDescription methodDescription) {
            Implementation.SpecialMethodInvocation specialMethodInvocationWithCheckedCompatibilityTo = this.implementationTarget.invokeDominant(methodDescription.asSignatureToken()).withCheckedCompatibilityTo(methodDescription.asTypeToken());
            if (!specialMethodInvocationWithCheckedCompatibilityTo.isValid()) {
                throw new IllegalStateException("Cannot call super (or default) method for " + methodDescription);
            }
            return new ByteCodeAppender.Size(new StackManipulation.Compound(MethodVariableAccess.allArgumentsOf(methodDescription).prependThisReference(), specialMethodInvocationWithCheckedCompatibilityTo, this.terminationHandler.of(methodDescription)).apply(methodVisitor, context).getMaximalSize(), methodDescription.getStackSize());
        }
    }
}
