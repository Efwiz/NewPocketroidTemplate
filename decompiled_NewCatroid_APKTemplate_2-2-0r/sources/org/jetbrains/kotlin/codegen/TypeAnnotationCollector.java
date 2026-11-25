package org.jetbrains.kotlin.codegen;

import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.load.kotlin.FileBasedKotlinClass;
import org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass;
import org.jetbrains.kotlin.load.kotlin.KotlinJvmBinarySourceElement;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedClassDescriptor;
import org.jetbrains.kotlin.types.TypeSystemCommonBackendContext;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.TypeArgumentMarker;
import org.jetbrains.kotlin.types.model.TypeVariance;

/* JADX INFO: compiled from: typeAnnotationCollector.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b&\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J*\u0010\n\u001a\u001e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\f0\u000bj\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\f`\r2\u0006\u0010\u000e\u001a\u00020\u000fJ\u0010\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013J\u000e\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\u0015J\u0012\u0010\u0016\u001a\b\u0012\u0004\u0012\u00028\u00000\u0017*\u00020\u000fH&J\f\u0010\u0018\u001a\u00020\u0019*\u00020\u000fH\u0002J\u0012\u0010\u001a\u001a\u00020\u0019*\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u001cR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\tX\u0082.¢\u0006\u0002\n\u0000¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/codegen/TypeAnnotationCollector;", ExifInterface.GPS_DIRECTION_TRUE, "", "context", "Lorg/jetbrains/kotlin/types/TypeSystemCommonBackendContext;", "(Lorg/jetbrains/kotlin/types/TypeSystemCommonBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/types/TypeSystemCommonBackendContext;", "state", "Lorg/jetbrains/kotlin/codegen/State;", "collectTypeAnnotations", "Ljava/util/ArrayList;", "Lorg/jetbrains/kotlin/codegen/TypePathInfo;", "Lkotlin/collections/ArrayList;", "kotlinType", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "isCompiledToJvm8OrHigher", "", "descriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "extractAnnotations", "", "gatherTypeAnnotations", "", "process", "step", "", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class TypeAnnotationCollector<T> {
    private final TypeSystemCommonBackendContext context;
    private State<T> state;

    public abstract List<T> extractAnnotations(KotlinTypeMarker kotlinTypeMarker);

    public TypeAnnotationCollector(TypeSystemCommonBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    public final TypeSystemCommonBackendContext getContext() {
        return this.context;
    }

    public final ArrayList<TypePathInfo<T>> collectTypeAnnotations(KotlinTypeMarker kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "kotlinType");
        this.state = new State<>(new ArrayList());
        gatherTypeAnnotations(kotlinType);
        State<T> state = this.state;
        if (state == null) {
            Intrinsics.throwUninitializedPropertyAccessException("state");
            state = null;
        }
        return state.getResults();
    }

    private final void gatherTypeAnnotations(KotlinTypeMarker kotlinTypeMarker) {
        TypeSystemCommonBackendContext typeSystemCommonBackendContext = this.context;
        if (typeSystemCommonBackendContext.isFlexible(kotlinTypeMarker)) {
            gatherTypeAnnotations(typeSystemCommonBackendContext.upperBoundIfFlexible(kotlinTypeMarker));
            return;
        }
        if (typeSystemCommonBackendContext.isInnerClass(typeSystemCommonBackendContext.typeConstructor(kotlinTypeMarker))) {
            return;
        }
        List<T> listExtractAnnotations = extractAnnotations(kotlinTypeMarker);
        State<T> state = null;
        if (listExtractAnnotations.isEmpty()) {
            listExtractAnnotations = null;
        }
        if (listExtractAnnotations != null) {
            State<T> state2 = this.state;
            if (state2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("state");
            } else {
                state = state2;
            }
            state.rememberAnnotations(listExtractAnnotations);
        }
        int iArgumentsCount = typeSystemCommonBackendContext.argumentsCount(kotlinTypeMarker);
        for (int i = 0; i < iArgumentsCount; i++) {
            TypeArgumentMarker argument = typeSystemCommonBackendContext.getArgument(kotlinTypeMarker, i);
            if (typeSystemCommonBackendContext.getVariance(argument) == TypeVariance.INV) {
                if (typeSystemCommonBackendContext.isArrayOrNullableArray(kotlinTypeMarker)) {
                    process(typeSystemCommonBackendContext.getType(argument), "[");
                } else {
                    process(typeSystemCommonBackendContext.getType(argument), new StringBuilder().append(i).append(';').toString());
                }
            }
        }
    }

    public final void process(KotlinTypeMarker kotlinTypeMarker, String step) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        Intrinsics.checkNotNullParameter(step, "step");
        State<T> state = this.state;
        State<T> state2 = null;
        if (state == null) {
            Intrinsics.throwUninitializedPropertyAccessException("state");
            state = null;
        }
        state.addStep(step);
        gatherTypeAnnotations(kotlinTypeMarker);
        State<T> state3 = this.state;
        if (state3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("state");
        } else {
            state2 = state3;
        }
        state2.removeStep();
    }

    public final boolean isCompiledToJvm8OrHigher(ClassDescriptor descriptor) {
        DeserializedClassDescriptor deserializedClassDescriptor = descriptor instanceof DeserializedClassDescriptor ? (DeserializedClassDescriptor) descriptor : null;
        if (deserializedClassDescriptor != null) {
            return isCompiledToJvm8OrHigher(deserializedClassDescriptor.getSourceElement());
        }
        return true;
    }

    public final boolean isCompiledToJvm8OrHigher(SourceElement source) {
        Intrinsics.checkNotNullParameter(source, "source");
        if (source instanceof KotlinJvmBinarySourceElement) {
            KotlinJvmBinaryClass binaryClass = ((KotlinJvmBinarySourceElement) source).getBinaryClass();
            FileBasedKotlinClass fileBasedKotlinClass = binaryClass instanceof FileBasedKotlinClass ? (FileBasedKotlinClass) binaryClass : null;
            if ((fileBasedKotlinClass != null ? fileBasedKotlinClass.getClassVersion() : 0) < 52) {
                return false;
            }
        }
        return true;
    }
}
