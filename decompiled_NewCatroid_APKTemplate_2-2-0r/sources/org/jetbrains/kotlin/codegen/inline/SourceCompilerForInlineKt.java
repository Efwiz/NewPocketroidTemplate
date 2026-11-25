package org.jetbrains.kotlin.codegen.inline;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.codegen.inline.coroutines.CoroutineTransformerKt;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.util.containers.SLRUMap;
import org.jetbrains.kotlin.config.CommonConfigurationKeys;
import org.jetbrains.kotlin.incremental.components.LocationInfo;
import org.jetbrains.kotlin.incremental.components.LookupTracker;
import org.jetbrains.kotlin.incremental.components.Position;
import org.jetbrains.kotlin.incremental.components.ScopeKind;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.Method;

/* JADX INFO: compiled from: SourceCompilerForInline.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000H\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a*\u0010\u0000\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0002\u001a0\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\tH\u0002\u001a.\u0010\u000b\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\u0010\u001a\"\u0010\u0011\u001a\u00020\u0012*\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018¨\u0006\u0019"}, d2 = {"getMethodNode", "Lorg/jetbrains/kotlin/codegen/inline/SMAPAndMethodNode;", "owner", "Lorg/jetbrains/org/objectweb/asm/Type;", "bytes", "", "method", "Lorg/jetbrains/org/objectweb/asm/commons/Method;", "isSuspend", "", "isMangled", "loadCompiledInlineFunction", "containerId", "Lorg/jetbrains/kotlin/name/ClassId;", "asmMethod", "state", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "trackLookup", "", "container", "Lorg/jetbrains/kotlin/name/FqName;", "functionName", "", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/incremental/components/LocationInfo;", Marker.BACKEND}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class SourceCompilerForInlineKt {
    public static final void trackLookup(GenerationState generationState, FqName container, String functionName, LocationInfo location) {
        Intrinsics.checkNotNullParameter(generationState, "<this>");
        Intrinsics.checkNotNullParameter(container, "container");
        Intrinsics.checkNotNullParameter(functionName, "functionName");
        Intrinsics.checkNotNullParameter(location, "location");
        LookupTracker lookupTracker = (LookupTracker) generationState.getConfiguration().get(CommonConfigurationKeys.LOOKUP_TRACKER);
        if (lookupTracker == null) {
            return;
        }
        synchronized (lookupTracker) {
            String filePath = location.getFilePath();
            Position position = lookupTracker.getRequiresPosition() ? location.getPosition() : Position.INSTANCE.getNO_POSITION();
            String strAsString = container.asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            lookupTracker.record(filePath, position, strAsString, ScopeKind.CLASSIFIER, functionName);
            Unit unit = Unit.INSTANCE;
        }
    }

    public static final SMAPAndMethodNode loadCompiledInlineFunction(ClassId containerId, Method asmMethod, boolean z, boolean z2, GenerationState state) {
        SMAPAndMethodNode methodNode;
        byte[] bArrContentsToByteArray;
        Intrinsics.checkNotNullParameter(containerId, "containerId");
        Intrinsics.checkNotNullParameter(asmMethod, "asmMethod");
        Intrinsics.checkNotNullParameter(state, "state");
        Type typeAsmTypeByClassId = AsmUtil.asmTypeByClassId(containerId);
        Intrinsics.checkNotNullExpressionValue(typeAsmTypeByClassId, "asmTypeByClassId(...)");
        SLRUMap<MethodId, SMAPAndMethodNode> methodNodeById = state.getInlineCache().getMethodNodeById();
        String descriptor = typeAsmTypeByClassId.getDescriptor();
        Intrinsics.checkNotNullExpressionValue(descriptor, "getDescriptor(...)");
        MethodId methodId = new MethodId(descriptor, asmMethod);
        synchronized (methodNodeById) {
            methodNode = methodNodeById.get(methodId);
            if (methodNode == null) {
                SLRUMap<String, byte[]> classBytes = state.getInlineCache().getClassBytes();
                String internalName = typeAsmTypeByClassId.getInternalName();
                Intrinsics.checkNotNullExpressionValue(internalName, "getInternalName(...)");
                synchronized (classBytes) {
                    bArrContentsToByteArray = classBytes.get(internalName);
                    if (bArrContentsToByteArray == null) {
                        VirtualFile virtualFileFindVirtualFile = InlineCodegenUtilsKt.findVirtualFile(state, containerId);
                        if (virtualFileFindVirtualFile == null || (bArrContentsToByteArray = virtualFileFindVirtualFile.contentsToByteArray()) == null) {
                            throw new IllegalStateException("Couldn't find declaration file for " + containerId);
                        }
                        Intrinsics.checkNotNull(bArrContentsToByteArray);
                        classBytes.put(internalName, bArrContentsToByteArray);
                    }
                }
                methodNode = getMethodNode(typeAsmTypeByClassId, bArrContentsToByteArray, asmMethod, z, z2);
                methodNodeById.put(methodId, methodNode);
            }
        }
        SMAPAndMethodNode sMAPAndMethodNode = methodNode;
        return new SMAPAndMethodNode(InlineCodegenUtilsKt.cloneMethodNode(sMAPAndMethodNode.getNode()), sMAPAndMethodNode.getClassSMAP());
    }

    private static final SMAPAndMethodNode getMethodNode(Type type, byte[] bArr, Method method, boolean z, boolean z2) {
        String name2;
        SMAPAndMethodNode methodNode;
        SMAPAndMethodNode methodNode2 = getMethodNode(type, bArr, method, z);
        if (methodNode2 != null) {
            return methodNode2;
        }
        if (z2) {
            String name3 = method.getName();
            Intrinsics.checkNotNullExpressionValue(name3, "getName(...)");
            int iIndexOf$default = StringsKt.indexOf$default((CharSequence) name3, '-', 0, false, 6, (Object) null);
            if (iIndexOf$default > 0) {
                String name4 = method.getName();
                Intrinsics.checkNotNullExpressionValue(name4, "getName(...)");
                name2 = name4.substring(0, iIndexOf$default);
                Intrinsics.checkNotNullExpressionValue(name2, "substring(...)");
            } else {
                name2 = method.getName();
            }
            if (!Intrinsics.areEqual(name2, method.getName()) && (methodNode = getMethodNode(type, bArr, new Method(name2, method.getDescriptor()), z)) != null) {
                return methodNode;
            }
            SMAPAndMethodNode methodNode3 = getMethodNode(type, bArr, new Method(name2 + JvmAbi.IMPL_SUFFIX_FOR_INLINE_CLASS_MEMBERS, method.getDescriptor()), z);
            if (methodNode3 != null) {
                return methodNode3;
            }
        }
        throw new IllegalStateException("couldn't find inline method " + type + '.' + method);
    }

    private static final SMAPAndMethodNode getMethodNode(Type type, byte[] bArr, Method method, boolean z) {
        SMAPAndMethodNode methodNode = z ? InlineCodegenUtilsKt.getMethodNode(bArr, type, new Method(method.getName() + CoroutineTransformerKt.FOR_INLINE_SUFFIX, method.getDescriptor())) : null;
        return methodNode == null ? InlineCodegenUtilsKt.getMethodNode(bArr, type, method) : methodNode;
    }
}
