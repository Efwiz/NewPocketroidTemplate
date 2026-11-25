package org.jetbrains.kotlin.constant;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner;

/* JADX INFO: compiled from: ConstantValue.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u000f\b\u0004\u0012\u0006\u0010\u0003\u001a\u00028\u0000¢\u0006\u0002\u0010\u0004¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/constant/UnsignedValueConstant;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/constant/ConstantValue;", "value", ReifiedTypeInliner.pluginIntrinsicsMarkerSignature, "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class UnsignedValueConstant<T> extends ConstantValue<T> {
    protected UnsignedValueConstant(T t) {
        super(t, null);
    }
}
