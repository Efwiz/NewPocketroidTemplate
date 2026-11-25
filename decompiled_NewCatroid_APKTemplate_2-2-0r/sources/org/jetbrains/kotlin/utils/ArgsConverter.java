package org.jetbrains.kotlin.utils;

import androidx.exifinterface.media.ExifInterface;
import com.android.dx.rop.code.RegisterSpec;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.reflect.KParameter;
import kotlin.sequences.Sequence;
import net.bytebuddy.description.method.ParameterDescription;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner;

/* JADX INFO: compiled from: parametersMap.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\bb\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002:\u0001\u000eJ\u001e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\bH&J2\u0010\t\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00000\b2\u0012\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\b0\fH&J2\u0010\r\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00000\b2\u0012\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\b0\fH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u000fÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/utils/ArgsConverter;", ExifInterface.GPS_DIRECTION_TRUE, "", "tryConvertSingle", "Lorg/jetbrains/kotlin/utils/ArgsConverter$Result;", "parameter", "Lkotlin/reflect/KParameter;", ParameterDescription.NAME_PREFIX, "Lorg/jetbrains/kotlin/utils/NamedArgument;", "tryConvertTail", "firstArg", "restArgs", "Lkotlin/sequences/Sequence;", "tryConvertVararg", "Result", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
interface ArgsConverter<T> {
    Result tryConvertSingle(KParameter parameter, NamedArgument<? extends T> arg);

    Result tryConvertTail(KParameter parameter, NamedArgument<? extends T> firstArg, Sequence<? extends NamedArgument<? extends T>> restArgs);

    Result tryConvertVararg(KParameter parameter, NamedArgument<? extends T> firstArg, Sequence<? extends NamedArgument<? extends T>> restArgs);

    /* JADX INFO: compiled from: parametersMap.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0002\u0003\u0004B\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0002\u0005\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/utils/ArgsConverter$Result;", "", "()V", "Failure", "Success", "Lorg/jetbrains/kotlin/utils/ArgsConverter$Result$Failure;", "Lorg/jetbrains/kotlin/utils/ArgsConverter$Result$Success;", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static abstract class Result {
        public /* synthetic */ Result(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: compiled from: parametersMap.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/utils/ArgsConverter$Result$Failure;", "Lorg/jetbrains/kotlin/utils/ArgsConverter$Result;", "()V", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Failure extends Result {
            public static final Failure INSTANCE = new Failure();

            private Failure() {
                super(null);
            }
        }

        private Result() {
        }

        /* JADX INFO: compiled from: parametersMap.kt */
        @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/utils/ArgsConverter$Result$Success;", "Lorg/jetbrains/kotlin/utils/ArgsConverter$Result;", RegisterSpec.PREFIX, "", ReifiedTypeInliner.pluginIntrinsicsMarkerSignature, "getV", "()Ljava/lang/Object;", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Success extends Result {
            private final Object v;

            public Success(Object obj) {
                super(null);
                this.v = obj;
            }

            public final Object getV() {
                return this.v;
            }
        }
    }
}
