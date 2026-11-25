package org.jetbrains.kotlin.codegen;

import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.resolve.calls.model.DefaultValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.ExpressionValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.VarargValueArgument;
import org.jetbrains.kotlin.utils.CollectionsKt;

/* JADX INFO: compiled from: ArgumentGenerator.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J.\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0016J\u0018\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0014J\u0018\u0010\u0011\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0012H\u0014J\u0018\u0010\u0013\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0007H\u0014J\u0018\u0010\u0014\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0015H\u0014J\u0016\u0010\u0016\u001a\u00020\f2\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00180\u0006H\u0014¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/codegen/ArgumentGenerator;", "", "()V", CompilerOptions.GENERATE, "Lorg/jetbrains/kotlin/codegen/DefaultCallArgs;", "valueArgumentsByIndex", "", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedValueArgument;", "actualArgs", "calleeDescriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "generateDefault", "", ASTExpr.DEFAULT_INDEX_VARIABLE_NAME, "", "argument", "Lorg/jetbrains/kotlin/resolve/calls/model/DefaultValueArgument;", "generateExpression", "Lorg/jetbrains/kotlin/resolve/calls/model/ExpressionValueArgument;", "generateOther", "generateVararg", "Lorg/jetbrains/kotlin/resolve/calls/model/VarargValueArgument;", "reorderArgumentsIfNeeded", "args", "Lorg/jetbrains/kotlin/codegen/ArgumentAndDeclIndex;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ArgumentGenerator {
    public DefaultCallArgs generate(List<? extends ResolvedValueArgument> valueArgumentsByIndex, List<? extends ResolvedValueArgument> actualArgs, CallableDescriptor calleeDescriptor) {
        CallableDescriptor callableDescriptorUnwrapFrontendVersion;
        List<ValueParameterDescriptor> valueParameters;
        Intrinsics.checkNotNullParameter(valueArgumentsByIndex, "valueArgumentsByIndex");
        Intrinsics.checkNotNullParameter(actualArgs, "actualArgs");
        valueArgumentsByIndex.size();
        actualArgs.size();
        Map mapMapToIndex = CollectionsKt.mapToIndex(valueArgumentsByIndex);
        ArrayList arrayList = new ArrayList();
        for (Object obj : actualArgs) {
            if (!(((ResolvedValueArgument) obj) instanceof DefaultValueArgument)) {
                arrayList.add(obj);
            }
        }
        ArrayList<ResolvedValueArgument> arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(kotlin.collections.CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        for (ResolvedValueArgument resolvedValueArgument : arrayList2) {
            Object obj2 = mapMapToIndex.get(resolvedValueArgument);
            Intrinsics.checkNotNull(obj2);
            arrayList3.add(new ArgumentAndDeclIndex(resolvedValueArgument, ((Number) obj2).intValue()));
        }
        List<ArgumentAndDeclIndex> mutableList = kotlin.collections.CollectionsKt.toMutableList((Collection) arrayList3);
        int size = 0;
        int i = 0;
        for (ResolvedValueArgument resolvedValueArgument2 : valueArgumentsByIndex) {
            int i2 = i + 1;
            if (resolvedValueArgument2 instanceof DefaultValueArgument) {
                mutableList.add(i, new ArgumentAndDeclIndex(resolvedValueArgument2, i));
            }
            i = i2;
        }
        if (calleeDescriptor != null && (callableDescriptorUnwrapFrontendVersion = CodegenUtilKt.unwrapFrontendVersion(calleeDescriptor)) != null && (valueParameters = callableDescriptorUnwrapFrontendVersion.getValueParameters()) != null) {
            size = valueParameters.size();
        }
        DefaultCallArgs defaultCallArgs = new DefaultCallArgs(size);
        for (ArgumentAndDeclIndex argumentAndDeclIndex : mutableList) {
            ResolvedValueArgument arg = argumentAndDeclIndex.getArg();
            int declIndex = argumentAndDeclIndex.getDeclIndex();
            if (arg instanceof ExpressionValueArgument) {
                generateExpression(declIndex, (ExpressionValueArgument) arg);
            } else if (arg instanceof DefaultValueArgument) {
                defaultCallArgs.mark(declIndex);
                generateDefault(declIndex, (DefaultValueArgument) arg);
            } else if (arg instanceof VarargValueArgument) {
                generateVararg(declIndex, (VarargValueArgument) arg);
            } else {
                generateOther(declIndex, arg);
            }
        }
        reorderArgumentsIfNeeded(mutableList);
        return defaultCallArgs;
    }

    protected void generateExpression(int i, ExpressionValueArgument argument) {
        Intrinsics.checkNotNullParameter(argument, "argument");
        throw new UnsupportedOperationException("Unsupported expression value argument #" + i + ": " + argument);
    }

    protected void generateDefault(int i, DefaultValueArgument argument) {
        Intrinsics.checkNotNullParameter(argument, "argument");
        throw new UnsupportedOperationException("Unsupported default value argument #" + i + ": " + argument);
    }

    protected void generateVararg(int i, VarargValueArgument argument) {
        Intrinsics.checkNotNullParameter(argument, "argument");
        throw new UnsupportedOperationException("Unsupported vararg value argument #" + i + ": " + argument);
    }

    protected void generateOther(int i, ResolvedValueArgument argument) {
        Intrinsics.checkNotNullParameter(argument, "argument");
        throw new UnsupportedOperationException("Unsupported value argument #" + i + ": " + argument);
    }

    protected void reorderArgumentsIfNeeded(List<ArgumentAndDeclIndex> args) {
        Intrinsics.checkNotNullParameter(args, "args");
        throw new UnsupportedOperationException("Unsupported operation");
    }
}
