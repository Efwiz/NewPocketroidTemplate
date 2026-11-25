package org.jetbrains.kotlin.codegen.inline;

import com.reandroid.dex.sections.Marker;
import java.util.Map;
import kotlin.Metadata;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodSignature;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;

/* JADX INFO: compiled from: SourceCompilerForInline.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000t\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\b\u0006\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH&J2\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020&2\b\u0010'\u001a\u0004\u0018\u00010&H&J\u0018\u0010(\u001a\u00020\u001a2\u0006\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020,H&J\u0016\u0010-\u001a\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0006\u0012\u0004\u0018\u00010&0.H&J\b\u0010/\u001a\u00020\u000eH&J\u0010\u00100\u001a\u00020\u000e2\u0006\u00101\u001a\u00020\u0006H&J\u0010\u00102\u001a\u00020\u001e2\u0006\u00103\u001a\u00020\u0006H&R\u0012\u0010\u0002\u001a\u00020\u0001X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004R\u0012\u0010\u0005\u001a\u00020\u0006X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0012\u0010\t\u001a\u00020\nX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0012\u0010\r\u001a\u00020\u000eX¦\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000fR\u0012\u0010\u0010\u001a\u00020\u000eX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u000fR\u0012\u0010\u0011\u001a\u00020\u0012X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u0012\u0010\u0015\u001a\u00020\u0016X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u00064À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/SourceCompilerForInline;", "", "callElement", "getCallElement", "()Ljava/lang/Object;", "callElementText", "", "getCallElementText", "()Ljava/lang/String;", "inlineCallSiteInfo", "Lorg/jetbrains/kotlin/codegen/inline/InlineCallSiteInfo;", "getInlineCallSiteInfo", "()Lorg/jetbrains/kotlin/codegen/inline/InlineCallSiteInfo;", "isCallInsideSameModuleAsCallee", "", "()Z", "isFinallyMarkerRequired", "sourceMapper", "Lorg/jetbrains/kotlin/codegen/inline/SourceMapper;", "getSourceMapper", "()Lorg/jetbrains/kotlin/codegen/inline/SourceMapper;", "state", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "getState", "()Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "compileInlineFunction", "Lorg/jetbrains/kotlin/codegen/inline/SMAPAndMethodNode;", "jvmSignature", "Lorg/jetbrains/kotlin/resolve/jvm/jvmSignature/JvmMethodSignature;", "generateFinallyBlocks", "", "finallyNode", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "curFinallyDepth", "", "returnType", "Lorg/jetbrains/org/objectweb/asm/Type;", "afterReturnLabel", "Lorg/jetbrains/org/objectweb/asm/Label;", "target", "generateLambdaBody", "lambdaInfo", "Lorg/jetbrains/kotlin/codegen/inline/ExpressionLambda;", "reifiedTypeParameters", "Lorg/jetbrains/kotlin/codegen/inline/ReifiedTypeParametersUsages;", "getContextLabels", "", "hasFinallyBlocks", "isSuspendLambdaCapturedByOuterObjectOrLambda", "name", "reportSuspensionPointInsideMonitor", "stackTraceElement", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface SourceCompilerForInline {
    SMAPAndMethodNode compileInlineFunction(JvmMethodSignature jvmSignature);

    void generateFinallyBlocks(MethodNode finallyNode, int curFinallyDepth, Type returnType, Label afterReturnLabel, Label target);

    SMAPAndMethodNode generateLambdaBody(ExpressionLambda lambdaInfo, ReifiedTypeParametersUsages reifiedTypeParameters);

    Object getCallElement();

    String getCallElementText();

    Map<String, Label> getContextLabels();

    InlineCallSiteInfo getInlineCallSiteInfo();

    SourceMapper getSourceMapper();

    GenerationState getState();

    boolean hasFinallyBlocks();

    boolean isCallInsideSameModuleAsCallee();

    boolean isFinallyMarkerRequired();

    boolean isSuspendLambdaCapturedByOuterObjectOrLambda(String name2);

    void reportSuspensionPointInsideMonitor(String stackTraceElement);
}
