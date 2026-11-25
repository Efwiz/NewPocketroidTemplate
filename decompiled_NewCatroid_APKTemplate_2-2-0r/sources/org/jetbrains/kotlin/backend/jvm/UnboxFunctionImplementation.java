package org.jetbrains.kotlin.backend.jvm;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: MfvcNodeFactory.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0004\n\u000b\f\rB\u0007\b\u0004¢\u0006\u0002\u0010\u0002J\u0011\u0010\u0007\u001a\u00020\u00002\u0006\u0010\b\u001a\u00020\tH\u0086\u0002R\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006\u0082\u0001\u0004\u000e\u000f\u0010\u0011¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/UnboxFunctionImplementation;", "", "()V", "hasPureUnboxMethod", "", "getHasPureUnboxMethod", "()Z", "get", "name", "Lorg/jetbrains/kotlin/name/Name;", "CustomUnboxFunctionImplementation", "DefaultUnboxFunctionImplementation", "DelegatingUnboxFunctionImplementation", "ExternalUnboxFunctionImplementation", "Lorg/jetbrains/kotlin/backend/jvm/UnboxFunctionImplementation$CustomUnboxFunctionImplementation;", "Lorg/jetbrains/kotlin/backend/jvm/UnboxFunctionImplementation$DefaultUnboxFunctionImplementation;", "Lorg/jetbrains/kotlin/backend/jvm/UnboxFunctionImplementation$DelegatingUnboxFunctionImplementation;", "Lorg/jetbrains/kotlin/backend/jvm/UnboxFunctionImplementation$ExternalUnboxFunctionImplementation;", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class UnboxFunctionImplementation {
    public /* synthetic */ UnboxFunctionImplementation(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract boolean getHasPureUnboxMethod();

    private UnboxFunctionImplementation() {
    }

    /* JADX INFO: compiled from: MfvcNodeFactory.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/UnboxFunctionImplementation$DefaultUnboxFunctionImplementation;", "Lorg/jetbrains/kotlin/backend/jvm/UnboxFunctionImplementation;", "()V", "hasPureUnboxMethod", "", "getHasPureUnboxMethod", "()Z", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class DefaultUnboxFunctionImplementation extends UnboxFunctionImplementation {
        public static final DefaultUnboxFunctionImplementation INSTANCE = new DefaultUnboxFunctionImplementation();

        @Override // org.jetbrains.kotlin.backend.jvm.UnboxFunctionImplementation
        public boolean getHasPureUnboxMethod() {
            return true;
        }

        private DefaultUnboxFunctionImplementation() {
            super(null);
        }
    }

    /* JADX INFO: compiled from: MfvcNodeFactory.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/UnboxFunctionImplementation$ExternalUnboxFunctionImplementation;", "Lorg/jetbrains/kotlin/backend/jvm/UnboxFunctionImplementation;", "()V", "hasPureUnboxMethod", "", "getHasPureUnboxMethod", "()Z", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ExternalUnboxFunctionImplementation extends UnboxFunctionImplementation {
        public static final ExternalUnboxFunctionImplementation INSTANCE = new ExternalUnboxFunctionImplementation();

        @Override // org.jetbrains.kotlin.backend.jvm.UnboxFunctionImplementation
        public boolean getHasPureUnboxMethod() {
            return false;
        }

        private ExternalUnboxFunctionImplementation() {
            super(null);
        }
    }

    /* JADX INFO: compiled from: MfvcNodeFactory.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\r\u001a\u00020\u00062\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fHÖ\u0003J\t\u0010\u0010\u001a\u00020\u0011HÖ\u0001J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001R\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/UnboxFunctionImplementation$DelegatingUnboxFunctionImplementation;", "Lorg/jetbrains/kotlin/backend/jvm/UnboxFunctionImplementation;", "node", "Lorg/jetbrains/kotlin/backend/jvm/NameableMfvcNode;", "(Lorg/jetbrains/kotlin/backend/jvm/NameableMfvcNode;)V", "hasPureUnboxMethod", "", "getHasPureUnboxMethod", "()Z", "getNode", "()Lorg/jetbrains/kotlin/backend/jvm/NameableMfvcNode;", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "", "hashCode", "", "toString", "", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class DelegatingUnboxFunctionImplementation extends UnboxFunctionImplementation {
        private final boolean hasPureUnboxMethod;
        private final NameableMfvcNode node;

        public static /* synthetic */ DelegatingUnboxFunctionImplementation copy$default(DelegatingUnboxFunctionImplementation delegatingUnboxFunctionImplementation, NameableMfvcNode nameableMfvcNode, int i, Object obj) {
            if ((i & 1) != 0) {
                nameableMfvcNode = delegatingUnboxFunctionImplementation.node;
            }
            return delegatingUnboxFunctionImplementation.copy(nameableMfvcNode);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final NameableMfvcNode getNode() {
            return this.node;
        }

        public final DelegatingUnboxFunctionImplementation copy(NameableMfvcNode node) {
            Intrinsics.checkNotNullParameter(node, "node");
            return new DelegatingUnboxFunctionImplementation(node);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            return (other instanceof DelegatingUnboxFunctionImplementation) && Intrinsics.areEqual(this.node, ((DelegatingUnboxFunctionImplementation) other).node);
        }

        public int hashCode() {
            return this.node.hashCode();
        }

        public String toString() {
            return "DelegatingUnboxFunctionImplementation(node=" + this.node + ')';
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public DelegatingUnboxFunctionImplementation(NameableMfvcNode node) {
            super(null);
            Intrinsics.checkNotNullParameter(node, "node");
            this.node = node;
            this.hasPureUnboxMethod = node.getHasPureUnboxMethod();
        }

        public final NameableMfvcNode getNode() {
            return this.node;
        }

        @Override // org.jetbrains.kotlin.backend.jvm.UnboxFunctionImplementation
        public boolean getHasPureUnboxMethod() {
            return this.hasPureUnboxMethod;
        }
    }

    /* JADX INFO: compiled from: MfvcNodeFactory.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\u0011\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u0012\u001a\u00020\n2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014HÖ\u0003J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/UnboxFunctionImplementation$CustomUnboxFunctionImplementation;", "Lorg/jetbrains/kotlin/backend/jvm/UnboxFunctionImplementation;", "getter", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "node", "Lorg/jetbrains/kotlin/backend/jvm/MfvcNode;", "(Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;Lorg/jetbrains/kotlin/backend/jvm/MfvcNode;)V", "getGetter", "()Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "hasPureUnboxMethod", "", "getHasPureUnboxMethod", "()Z", "getNode", "()Lorg/jetbrains/kotlin/backend/jvm/MfvcNode;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "", "hashCode", "", "toString", "", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class CustomUnboxFunctionImplementation extends UnboxFunctionImplementation {
        private final IrSimpleFunction getter;
        private final MfvcNode node;

        public static /* synthetic */ CustomUnboxFunctionImplementation copy$default(CustomUnboxFunctionImplementation customUnboxFunctionImplementation, IrSimpleFunction irSimpleFunction, MfvcNode mfvcNode, int i, Object obj) {
            if ((i & 1) != 0) {
                irSimpleFunction = customUnboxFunctionImplementation.getter;
            }
            if ((i & 2) != 0) {
                mfvcNode = customUnboxFunctionImplementation.node;
            }
            return customUnboxFunctionImplementation.copy(irSimpleFunction, mfvcNode);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final IrSimpleFunction getGetter() {
            return this.getter;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final MfvcNode getNode() {
            return this.node;
        }

        public final CustomUnboxFunctionImplementation copy(IrSimpleFunction getter, MfvcNode node) {
            Intrinsics.checkNotNullParameter(getter, "getter");
            Intrinsics.checkNotNullParameter(node, "node");
            return new CustomUnboxFunctionImplementation(getter, node);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof CustomUnboxFunctionImplementation)) {
                return false;
            }
            CustomUnboxFunctionImplementation customUnboxFunctionImplementation = (CustomUnboxFunctionImplementation) other;
            return Intrinsics.areEqual(this.getter, customUnboxFunctionImplementation.getter) && Intrinsics.areEqual(this.node, customUnboxFunctionImplementation.node);
        }

        @Override // org.jetbrains.kotlin.backend.jvm.UnboxFunctionImplementation
        public boolean getHasPureUnboxMethod() {
            return false;
        }

        public int hashCode() {
            return (this.getter.hashCode() * 31) + this.node.hashCode();
        }

        public String toString() {
            return "CustomUnboxFunctionImplementation(getter=" + this.getter + ", node=" + this.node + ')';
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public CustomUnboxFunctionImplementation(IrSimpleFunction getter, MfvcNode node) {
            super(null);
            Intrinsics.checkNotNullParameter(getter, "getter");
            Intrinsics.checkNotNullParameter(node, "node");
            this.getter = getter;
            this.node = node;
        }

        public final IrSimpleFunction getGetter() {
            return this.getter;
        }

        public final MfvcNode getNode() {
            return this.node;
        }
    }

    private static final NameableMfvcNode get$nextNode(Name name2, MfvcNode mfvcNode) {
        Intrinsics.checkNotNull(mfvcNode, "null cannot be cast to non-null type org.jetbrains.kotlin.backend.jvm.MfvcNodeWithSubnodes");
        NameableMfvcNode nameableMfvcNode = ((MfvcNodeWithSubnodes) mfvcNode).get(name2);
        Intrinsics.checkNotNull(nameableMfvcNode);
        return nameableMfvcNode;
    }

    public final UnboxFunctionImplementation get(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        if (this instanceof CustomUnboxFunctionImplementation) {
            CustomUnboxFunctionImplementation customUnboxFunctionImplementation = (CustomUnboxFunctionImplementation) this;
            return CustomUnboxFunctionImplementation.copy$default(customUnboxFunctionImplementation, null, get$nextNode(name2, customUnboxFunctionImplementation.getNode()), 1, null);
        }
        if (this instanceof DelegatingUnboxFunctionImplementation) {
            DelegatingUnboxFunctionImplementation delegatingUnboxFunctionImplementation = (DelegatingUnboxFunctionImplementation) this;
            return delegatingUnboxFunctionImplementation.copy(get$nextNode(name2, delegatingUnboxFunctionImplementation.getNode()));
        }
        if (Intrinsics.areEqual(this, DefaultUnboxFunctionImplementation.INSTANCE)) {
            return DefaultUnboxFunctionImplementation.INSTANCE;
        }
        if (Intrinsics.areEqual(this, ExternalUnboxFunctionImplementation.INSTANCE)) {
            return ExternalUnboxFunctionImplementation.INSTANCE;
        }
        throw new NoWhenBranchMatchedException();
    }
}
