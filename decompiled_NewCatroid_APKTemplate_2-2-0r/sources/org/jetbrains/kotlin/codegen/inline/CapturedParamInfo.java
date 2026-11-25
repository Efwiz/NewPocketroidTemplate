package org.jetbrains.kotlin.codegen.inline;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.codegen.StackValue;

/* JADX INFO: compiled from: CapturedParamInfo.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0013\u0018\u0000 \u001f2\u00020\u0001:\u0001\u001fB7\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\t\u0012\u0006\u0010\u000b\u001a\u00020\u0007¢\u0006\u0002\u0010\fBA\b\u0012\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\b\u0010\n\u001a\u0004\u0018\u00010\r\u0012\u0006\u0010\u000b\u001a\u00020\u0007\u0012\u0006\u0010\u000e\u001a\u00020\t¢\u0006\u0002\u0010\u000fJ\u000e\u0010\u001d\u001a\u00020\u00002\u0006\u0010\u001e\u001a\u00020\tR\u0011\u0010\u0010\u001a\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0015\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0017\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0016\"\u0004\b\u0018\u0010\u0019R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0012R\u0011\u0010\u001b\u001a\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u0012¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/CapturedParamInfo;", "Lorg/jetbrains/kotlin/codegen/inline/ParameterInfo;", "desc", "Lorg/jetbrains/kotlin/codegen/inline/CapturedParamDesc;", "newFieldName", "", "skipped", "", "index", "", "remapIndex", "skipInConstructor", "(Lorg/jetbrains/kotlin/codegen/inline/CapturedParamDesc;Ljava/lang/String;ZIIZ)V", "Lorg/jetbrains/kotlin/codegen/StackValue;", "declarationIndex", "(Lorg/jetbrains/kotlin/codegen/inline/CapturedParamDesc;Ljava/lang/String;ZILorg/jetbrains/kotlin/codegen/StackValue;ZI)V", "containingLambdaName", "getContainingLambdaName", "()Ljava/lang/String;", "getDesc", "()Lorg/jetbrains/kotlin/codegen/inline/CapturedParamDesc;", "isSkipInConstructor", "()Z", "isSynthetic", "setSynthetic", "(Z)V", "getNewFieldName", "originalFieldName", "getOriginalFieldName", "cloneWithNewDeclarationIndex", "newDeclarationIndex", "Companion", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CapturedParamInfo extends ParameterInfo {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final CapturedParamDesc desc;
    private final boolean isSkipInConstructor;
    private boolean isSynthetic;
    private final String newFieldName;

    public final CapturedParamDesc getDesc() {
        return this.desc;
    }

    public final String getNewFieldName() {
        return this.newFieldName;
    }

    /* JADX INFO: renamed from: isSkipInConstructor, reason: from getter */
    public final boolean getIsSkipInConstructor() {
        return this.isSkipInConstructor;
    }

    /* JADX INFO: renamed from: isSynthetic, reason: from getter */
    public final boolean getIsSynthetic() {
        return this.isSynthetic;
    }

    public final void setSynthetic(boolean z) {
        this.isSynthetic = z;
    }

    public final String getOriginalFieldName() {
        return this.desc.getFieldName();
    }

    public final String getContainingLambdaName() {
        return this.desc.getContainingLambdaName();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CapturedParamInfo(CapturedParamDesc desc, String newFieldName, boolean z, int i, int i2, boolean z2) {
        super(desc.getType(), z, i, i2, -1);
        Intrinsics.checkNotNullParameter(desc, "desc");
        Intrinsics.checkNotNullParameter(newFieldName, "newFieldName");
        this.desc = desc;
        this.newFieldName = newFieldName;
        this.isSkipInConstructor = z2;
    }

    private CapturedParamInfo(CapturedParamDesc capturedParamDesc, String str, boolean z, int i, StackValue stackValue, boolean z2, int i2) {
        super(capturedParamDesc.getType(), z, i, stackValue, i2, null, 32, null);
        this.desc = capturedParamDesc;
        this.newFieldName = str;
        this.isSkipInConstructor = z2;
    }

    public final CapturedParamInfo cloneWithNewDeclarationIndex(int newDeclarationIndex) {
        CapturedParamInfo capturedParamInfo = new CapturedParamInfo(this.desc, this.newFieldName, getIsSkipped(), getIndex(), getRemapValue(), this.isSkipInConstructor, newDeclarationIndex);
        capturedParamInfo.setFunctionalArgument(getFunctionalArgument());
        capturedParamInfo.isSynthetic = this.isSynthetic;
        return capturedParamInfo;
    }

    /* JADX INFO: compiled from: CapturedParamInfo.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/CapturedParamInfo$Companion;", "", "()V", "isSynthetic", "", CompilerOptions.INFO, "Lorg/jetbrains/kotlin/codegen/inline/ParameterInfo;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final boolean isSynthetic(ParameterInfo info) {
            Intrinsics.checkNotNullParameter(info, "info");
            return (info instanceof CapturedParamInfo) && ((CapturedParamInfo) info).getIsSynthetic();
        }
    }
}
