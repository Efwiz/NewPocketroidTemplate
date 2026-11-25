package org.jetbrains.kotlin.codegen.inline;

import androidx.exifinterface.media.ExifInterface;
import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.StackValue;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: ParametersBuilder.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\u0018\u0000 /2\u00020\u0001:\u0001/B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015J\u0016\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0016\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u0013J8\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u00132\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u001a\u001a\u00020\u00182\u0006\u0010\u001b\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u000bJ\u000e\u0010\u001c\u001a\u00020\u000f2\u0006\u0010\u001d\u001a\u00020\u000fJ \u0010\u001e\u001a\u00020\u000b2\u0006\u0010\u001a\u001a\u00020\u00182\u0006\u0010\u001b\u001a\u00020\u00152\b\b\u0002\u0010\u001f\u001a\u00020\u0018J(\u0010 \u001a\u00020\u000b2\u0006\u0010\u001a\u001a\u00020\u00182\u0006\u0010\u001b\u001a\u00020\u00152\b\u0010!\u001a\u0004\u0018\u00010\"2\u0006\u0010#\u001a\u00020\u0004J'\u0010$\u001a\u0002H%\"\b\b\u0000\u0010%*\u00020\u000b2\u0006\u0010&\u001a\u0002H%2\u0006\u0010'\u001a\u00020\u0015H\u0002¢\u0006\u0002\u0010(J\u0016\u0010)\u001a\u00020\u000b2\u0006\u0010\u001a\u001a\u00020\u00182\u0006\u0010\u001b\u001a\u00020\u0015J\u0006\u0010*\u001a\u00020+J\f\u0010,\u001a\b\u0012\u0004\u0012\u00020\u000b0-J\f\u0010.\u001a\b\u0012\u0004\u0012\u00020\u000f0-R\u001e\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0004@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u000e\u0010\b\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001e\u0010\t\u001a\u0012\u0012\u0004\u0012\u00020\u000b0\nj\b\u0012\u0004\u0012\u00020\u000b`\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u00060"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/ParametersBuilder;", "", "()V", "<set-?>", "", "nextParameterOffset", "getNextParameterOffset", "()I", "nextValueParameterIndex", "params", "Ljava/util/ArrayList;", "Lorg/jetbrains/kotlin/codegen/inline/ParameterInfo;", "Lkotlin/collections/ArrayList;", "valueParamFirstIndex", "addCapturedParam", "Lorg/jetbrains/kotlin/codegen/inline/CapturedParamInfo;", "desc", "Lorg/jetbrains/kotlin/codegen/inline/CapturedParamDesc;", "newFieldName", "", "skipInConstructor", "", "original", "containingLambdaType", "Lorg/jetbrains/org/objectweb/asm/Type;", "fieldName", ModuleXmlParser.TYPE, "skipped", "addCapturedParamCopy", "copyFrom", "addNextParameter", "typeOnStack", "addNextValueParameter", "remapValue", "Lorg/jetbrains/kotlin/codegen/StackValue;", "parameterIndex", "addParameter", ExifInterface.GPS_DIRECTION_TRUE, CompilerOptions.INFO, "isValueParameter", "(Lorg/jetbrains/kotlin/codegen/inline/ParameterInfo;Z)Lorg/jetbrains/kotlin/codegen/inline/ParameterInfo;", "addThis", "buildParameters", "Lorg/jetbrains/kotlin/codegen/inline/Parameters;", "listAllParams", "", "listCaptured", "Companion", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ParametersBuilder {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private int nextParameterOffset;
    private int nextValueParameterIndex;
    private final ArrayList<ParameterInfo> params;
    private int valueParamFirstIndex;

    public /* synthetic */ ParametersBuilder(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    @JvmStatic
    public static final ParametersBuilder newBuilder() {
        return INSTANCE.newBuilder();
    }

    private ParametersBuilder() {
        this.params = new ArrayList<>();
    }

    public final int getNextParameterOffset() {
        return this.nextParameterOffset;
    }

    public final ParameterInfo addThis(Type type, boolean skipped) {
        Intrinsics.checkNotNullParameter(type, "type");
        return addParameter(new ParameterInfo(type, skipped, this.nextParameterOffset, -1, this.nextValueParameterIndex), false);
    }

    public static /* synthetic */ ParameterInfo addNextParameter$default(ParametersBuilder parametersBuilder, Type type, boolean z, Type type2, int i, Object obj) {
        if ((i & 4) != 0) {
            type2 = type;
        }
        return parametersBuilder.addNextParameter(type, z, type2);
    }

    public final ParameterInfo addNextParameter(Type type, boolean skipped, Type typeOnStack) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(typeOnStack, "typeOnStack");
        return addParameter(new ParameterInfo(type, skipped, this.nextParameterOffset, null, this.nextValueParameterIndex, typeOnStack), false);
    }

    public final ParameterInfo addNextValueParameter(Type type, boolean skipped, StackValue remapValue, int parameterIndex) {
        Intrinsics.checkNotNullParameter(type, "type");
        return addParameter(new ParameterInfo(type, skipped, this.nextParameterOffset, remapValue, parameterIndex + this.valueParamFirstIndex, null, 32, null), true);
    }

    public final CapturedParamInfo addCapturedParam(CapturedParamInfo original, String newFieldName) {
        Intrinsics.checkNotNullParameter(original, "original");
        Intrinsics.checkNotNullParameter(newFieldName, "newFieldName");
        CapturedParamInfo capturedParamInfo = new CapturedParamInfo(original.getDesc(), newFieldName, original.getIsSkipped(), this.nextParameterOffset, original.getIndex(), original.getIsSkipInConstructor());
        capturedParamInfo.setFunctionalArgument(original.getFunctionalArgument());
        return (CapturedParamInfo) addParameter(capturedParamInfo, false);
    }

    public final CapturedParamInfo addCapturedParam(CapturedParamDesc desc, String newFieldName, boolean skipInConstructor) {
        Intrinsics.checkNotNullParameter(desc, "desc");
        Intrinsics.checkNotNullParameter(newFieldName, "newFieldName");
        return (CapturedParamInfo) addParameter(new CapturedParamInfo(desc, newFieldName, false, this.nextParameterOffset, -1, skipInConstructor), false);
    }

    public final CapturedParamInfo addCapturedParamCopy(CapturedParamInfo copyFrom) {
        Intrinsics.checkNotNullParameter(copyFrom, "copyFrom");
        return (CapturedParamInfo) addParameter(copyFrom.cloneWithNewDeclarationIndex(-1), false);
    }

    public final CapturedParamInfo addCapturedParam(Type containingLambdaType, String fieldName, String newFieldName, Type type, boolean skipped, ParameterInfo original) {
        Intrinsics.checkNotNullParameter(containingLambdaType, "containingLambdaType");
        Intrinsics.checkNotNullParameter(fieldName, "fieldName");
        Intrinsics.checkNotNullParameter(newFieldName, "newFieldName");
        Intrinsics.checkNotNullParameter(type, "type");
        CapturedParamInfo capturedParamInfo = new CapturedParamInfo(new CapturedParamDesc(containingLambdaType, fieldName, type, false, 8, null), newFieldName, skipped, this.nextParameterOffset, original != null ? original.getIndex() : -1, false);
        if (original != null) {
            capturedParamInfo.setFunctionalArgument(original.getFunctionalArgument());
        }
        return (CapturedParamInfo) addParameter(capturedParamInfo, false);
    }

    private final <T extends ParameterInfo> T addParameter(T info, boolean isValueParameter) {
        this.params.add(info);
        this.nextParameterOffset += info.getType().getSize();
        if (!(info instanceof CapturedParamInfo)) {
            this.nextValueParameterIndex++;
            if (!isValueParameter) {
                this.valueParamFirstIndex++;
            }
        }
        return info;
    }

    public final List<CapturedParamInfo> listCaptured() {
        ArrayList<ParameterInfo> arrayList = this.params;
        ArrayList arrayList2 = new ArrayList();
        for (Object obj : arrayList) {
            if (obj instanceof CapturedParamInfo) {
                arrayList2.add(obj);
            }
        }
        return arrayList2;
    }

    public final List<ParameterInfo> listAllParams() {
        return this.params;
    }

    public final Parameters buildParameters() {
        Integer num;
        Iterator<T> it = this.params.iterator();
        if (it.hasNext()) {
            Integer numValueOf = Integer.valueOf(((ParameterInfo) it.next()).getDeclarationIndex());
            while (it.hasNext()) {
                Integer numValueOf2 = Integer.valueOf(((ParameterInfo) it.next()).getDeclarationIndex());
                if (numValueOf.compareTo(numValueOf2) < 0) {
                    numValueOf = numValueOf2;
                }
            }
            num = numValueOf;
        } else {
            num = null;
        }
        Integer num2 = num;
        int iIntValue = (num2 != null ? num2.intValue() : -1) + 1;
        ArrayList<ParameterInfo> arrayList = this.params;
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList, 10));
        for (CapturedParamInfo capturedParamInfoCloneWithNewDeclarationIndex : arrayList) {
            if (capturedParamInfoCloneWithNewDeclarationIndex instanceof CapturedParamInfo) {
                capturedParamInfoCloneWithNewDeclarationIndex = ((CapturedParamInfo) capturedParamInfoCloneWithNewDeclarationIndex).cloneWithNewDeclarationIndex(iIntValue);
                iIntValue++;
            }
            arrayList2.add(capturedParamInfoCloneWithNewDeclarationIndex);
        }
        return new Parameters(arrayList2);
    }

    /* JADX INFO: compiled from: ParametersBuilder.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0007¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/ParametersBuilder$Companion;", "", "()V", "newBuilder", "Lorg/jetbrains/kotlin/codegen/inline/ParametersBuilder;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final ParametersBuilder newBuilder() {
            return new ParametersBuilder(null);
        }
    }
}
