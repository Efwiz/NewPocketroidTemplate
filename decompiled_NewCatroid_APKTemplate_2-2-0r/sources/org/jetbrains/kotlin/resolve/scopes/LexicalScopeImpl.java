package org.jetbrains.kotlin.resolve.scopes;

import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.utils.Printer;

/* JADX INFO: compiled from: LexicalScopeImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002:\u0001%Bh\b\u0007\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n\u0012\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\n0\f\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u0012\u001d\b\u0002\u0010\u0011\u001a\u0017\u0012\b\u0012\u00060\u0013R\u00020\u0000\u0012\u0004\u0012\u00020\u00140\u0012¢\u0006\u0002\b\u0015¢\u0006\u0002\u0010\u0016J\u0010\u0010 \u001a\u00020\u00142\u0006\u0010!\u001a\u00020\"H\u0016J\b\u0010#\u001a\u00020$H\u0016R\u001a\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\n0\fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0016\u0010\t\u001a\u0004\u0018\u00010\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u001bR\u0014\u0010\r\u001a\u00020\u000eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001f¨\u0006&"}, d2 = {"Lorg/jetbrains/kotlin/resolve/scopes/LexicalScopeImpl;", "Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;", "Lorg/jetbrains/kotlin/resolve/scopes/LexicalScopeStorage;", "parent", "Lorg/jetbrains/kotlin/resolve/scopes/HierarchicalScope;", "ownerDescriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "isOwnerDescriptorAccessibleByLabel", "", "implicitReceiver", "Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "contextReceiversGroup", "", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/resolve/scopes/LexicalScopeKind;", "redeclarationChecker", "Lorg/jetbrains/kotlin/resolve/scopes/LocalRedeclarationChecker;", "initialize", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/resolve/scopes/LexicalScopeImpl$InitializeHandler;", "", "Lkotlin/ExtensionFunctionType;", "(Lorg/jetbrains/kotlin/resolve/scopes/HierarchicalScope;Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;ZLorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;Ljava/util/List;Lorg/jetbrains/kotlin/resolve/scopes/LexicalScopeKind;Lorg/jetbrains/kotlin/resolve/scopes/LocalRedeclarationChecker;Lkotlin/jvm/functions/Function1;)V", "getContextReceiversGroup", "()Ljava/util/List;", "getImplicitReceiver", "()Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "()Z", "getKind", "()Lorg/jetbrains/kotlin/resolve/scopes/LexicalScopeKind;", "getOwnerDescriptor", "()Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "printStructure", "p", "Lorg/jetbrains/kotlin/utils/Printer;", "toString", "", "InitializeHandler", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LexicalScopeImpl extends LexicalScopeStorage implements LexicalScope {
    private final List<ReceiverParameterDescriptor> contextReceiversGroup;
    private final ReceiverParameterDescriptor implicitReceiver;
    private final boolean isOwnerDescriptorAccessibleByLabel;
    private final LexicalScopeKind kind;
    private final DeclarationDescriptor ownerDescriptor;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public LexicalScopeImpl(HierarchicalScope parent, DeclarationDescriptor ownerDescriptor, boolean z, ReceiverParameterDescriptor receiverParameterDescriptor, List<? extends ReceiverParameterDescriptor> contextReceiversGroup, LexicalScopeKind kind) {
        this(parent, ownerDescriptor, z, receiverParameterDescriptor, contextReceiversGroup, kind, null, null, 192, null);
        Intrinsics.checkNotNullParameter(parent, "parent");
        Intrinsics.checkNotNullParameter(ownerDescriptor, "ownerDescriptor");
        Intrinsics.checkNotNullParameter(contextReceiversGroup, "contextReceiversGroup");
        Intrinsics.checkNotNullParameter(kind, "kind");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public LexicalScopeImpl(HierarchicalScope parent, DeclarationDescriptor ownerDescriptor, boolean z, ReceiverParameterDescriptor receiverParameterDescriptor, List<? extends ReceiverParameterDescriptor> contextReceiversGroup, LexicalScopeKind kind, LocalRedeclarationChecker redeclarationChecker) {
        this(parent, ownerDescriptor, z, receiverParameterDescriptor, contextReceiversGroup, kind, redeclarationChecker, null, 128, null);
        Intrinsics.checkNotNullParameter(parent, "parent");
        Intrinsics.checkNotNullParameter(ownerDescriptor, "ownerDescriptor");
        Intrinsics.checkNotNullParameter(contextReceiversGroup, "contextReceiversGroup");
        Intrinsics.checkNotNullParameter(kind, "kind");
        Intrinsics.checkNotNullParameter(redeclarationChecker, "redeclarationChecker");
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.LexicalScope
    public DeclarationDescriptor getOwnerDescriptor() {
        return this.ownerDescriptor;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.LexicalScope
    /* JADX INFO: renamed from: isOwnerDescriptorAccessibleByLabel, reason: from getter */
    public boolean getIsOwnerDescriptorAccessibleByLabel() {
        return this.isOwnerDescriptorAccessibleByLabel;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.LexicalScope
    public ReceiverParameterDescriptor getImplicitReceiver() {
        return this.implicitReceiver;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.LexicalScope
    public List<ReceiverParameterDescriptor> getContextReceiversGroup() {
        return this.contextReceiversGroup;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.LexicalScope
    public LexicalScopeKind getKind() {
        return this.kind;
    }

    public /* synthetic */ LexicalScopeImpl(HierarchicalScope hierarchicalScope, DeclarationDescriptor declarationDescriptor, boolean z, ReceiverParameterDescriptor receiverParameterDescriptor, List list, LexicalScopeKind lexicalScopeKind, LocalRedeclarationChecker localRedeclarationChecker, Function1 function1, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(hierarchicalScope, declarationDescriptor, z, receiverParameterDescriptor, list, lexicalScopeKind, (i & 64) != 0 ? LocalRedeclarationChecker.DO_NOTHING.INSTANCE : localRedeclarationChecker, (i & 128) != 0 ? new Function1<InitializeHandler, Unit>() { // from class: org.jetbrains.kotlin.resolve.scopes.LexicalScopeImpl.1
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(InitializeHandler initializeHandler) {
                Intrinsics.checkNotNullParameter(initializeHandler, "$this$null");
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(InitializeHandler initializeHandler) {
                invoke2(initializeHandler);
                return Unit.INSTANCE;
            }
        } : function1);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public LexicalScopeImpl(HierarchicalScope parent, DeclarationDescriptor ownerDescriptor, boolean z, ReceiverParameterDescriptor receiverParameterDescriptor, List<? extends ReceiverParameterDescriptor> contextReceiversGroup, LexicalScopeKind kind, LocalRedeclarationChecker redeclarationChecker, Function1<? super InitializeHandler, Unit> initialize) {
        super(parent, redeclarationChecker);
        Intrinsics.checkNotNullParameter(parent, "parent");
        Intrinsics.checkNotNullParameter(ownerDescriptor, "ownerDescriptor");
        Intrinsics.checkNotNullParameter(contextReceiversGroup, "contextReceiversGroup");
        Intrinsics.checkNotNullParameter(kind, "kind");
        Intrinsics.checkNotNullParameter(redeclarationChecker, "redeclarationChecker");
        Intrinsics.checkNotNullParameter(initialize, "initialize");
        this.ownerDescriptor = ownerDescriptor;
        this.isOwnerDescriptorAccessibleByLabel = z;
        this.implicitReceiver = receiverParameterDescriptor;
        this.contextReceiversGroup = contextReceiversGroup;
        this.kind = kind;
        initialize.invoke(new InitializeHandler());
    }

    public String toString() {
        return getKind().toString();
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.HierarchicalScope
    public void printStructure(Printer p) {
        Intrinsics.checkNotNullParameter(p, "p");
        String simpleName = getClass().getSimpleName();
        LexicalScopeKind kind = getKind();
        Name name2 = getOwnerDescriptor().getShortName();
        ReceiverParameterDescriptor implicitReceiver = getImplicitReceiver();
        ReceiverValue value = implicitReceiver != null ? implicitReceiver.getValue() : null;
        p.println(simpleName, ": ", kind, "; for descriptor: ", name2, " with implicitReceiver: ", value == null ? "NONE" : value, " with contextReceiversGroup: ", getContextReceiversGroup().isEmpty() ? "NONE" : CollectionsKt.joinToString$default(getContextReceiversGroup(), null, null, null, 0, null, new Function1<ReceiverParameterDescriptor, CharSequence>() { // from class: org.jetbrains.kotlin.resolve.scopes.LexicalScopeImpl.printStructure.1
            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(ReceiverParameterDescriptor it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.getValue().toString();
            }
        }, 31, null), " {");
        p.pushIndent();
        p.print("parent = ");
        HierarchicalScope parent = getParent();
        Printer printerWithholdIndentOnce = p.withholdIndentOnce();
        Intrinsics.checkNotNullExpressionValue(printerWithholdIndentOnce, "withholdIndentOnce(...)");
        parent.printStructure(printerWithholdIndentOnce);
        p.popIndent();
        p.println("}");
    }

    /* JADX INFO: compiled from: LexicalScopeImpl.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tJ\u000e\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/resolve/scopes/LexicalScopeImpl$InitializeHandler;", "", "(Lorg/jetbrains/kotlin/resolve/scopes/LexicalScopeImpl;)V", "addClassifierDescriptor", "", "classifierDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", "addFunctionDescriptor", "functionDescriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "addVariableDescriptor", "variableDescriptor", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public final class InitializeHandler {
        public InitializeHandler() {
        }

        public final void addVariableDescriptor(VariableDescriptor variableDescriptor) {
            Intrinsics.checkNotNullParameter(variableDescriptor, "variableDescriptor");
            LexicalScopeImpl.this.addVariableOrClassDescriptor(variableDescriptor);
        }

        public final void addFunctionDescriptor(FunctionDescriptor functionDescriptor) {
            Intrinsics.checkNotNullParameter(functionDescriptor, "functionDescriptor");
            LexicalScopeImpl.this.addFunctionDescriptorInternal(functionDescriptor);
        }

        public final void addClassifierDescriptor(ClassifierDescriptor classifierDescriptor) {
            Intrinsics.checkNotNullParameter(classifierDescriptor, "classifierDescriptor");
            LexicalScopeImpl.this.addVariableOrClassDescriptor(classifierDescriptor);
        }
    }
}
