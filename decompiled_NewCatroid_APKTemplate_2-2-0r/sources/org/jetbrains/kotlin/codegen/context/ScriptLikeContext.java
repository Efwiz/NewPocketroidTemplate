package org.jetbrains.kotlin.codegen.context;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.OwnerKind;
import org.jetbrains.kotlin.codegen.StackValue;
import org.jetbrains.kotlin.codegen.binding.MutableClosure;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapper;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;

/* JADX INFO: compiled from: ScriptLikeContext.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0007¢\u0006\u0002\u0010\bJ\u001a\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u001a\u0010\u0011\u001a\u00020\f2\b\u0010\u0012\u001a\u0004\u0018\u00010\f2\u0006\u0010\u0013\u001a\u00020\u0005H&R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/codegen/context/ScriptLikeContext;", "Lorg/jetbrains/kotlin/codegen/context/ClassContext;", "typeMapper", "Lorg/jetbrains/kotlin/codegen/state/KotlinTypeMapper;", "contextDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "parentContext", "Lorg/jetbrains/kotlin/codegen/context/CodegenContext;", "(Lorg/jetbrains/kotlin/codegen/state/KotlinTypeMapper;Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;Lorg/jetbrains/kotlin/codegen/context/CodegenContext;)V", "getTypeMapper", "()Lorg/jetbrains/kotlin/codegen/state/KotlinTypeMapper;", "captureVariable", "Lorg/jetbrains/kotlin/codegen/StackValue;", "closure", "Lorg/jetbrains/kotlin/codegen/binding/MutableClosure;", "target", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "getOuterReceiverExpression", "prefix", "thisOrOuterClass", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ScriptLikeContext extends ClassContext {
    private final KotlinTypeMapper typeMapper;

    public StackValue captureVariable(MutableClosure closure, DeclarationDescriptor target) {
        Intrinsics.checkNotNullParameter(closure, "closure");
        Intrinsics.checkNotNullParameter(target, "target");
        return null;
    }

    public abstract StackValue getOuterReceiverExpression(StackValue prefix, ClassDescriptor thisOrOuterClass);

    public final KotlinTypeMapper getTypeMapper() {
        return this.typeMapper;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ScriptLikeContext(KotlinTypeMapper typeMapper, ClassDescriptor contextDescriptor, CodegenContext<?> codegenContext) {
        super(typeMapper, contextDescriptor, OwnerKind.IMPLEMENTATION, codegenContext, null);
        Intrinsics.checkNotNullParameter(typeMapper, "typeMapper");
        Intrinsics.checkNotNullParameter(contextDescriptor, "contextDescriptor");
        this.typeMapper = typeMapper;
    }
}
