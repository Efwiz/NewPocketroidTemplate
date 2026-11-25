package org.catrobat.catroid.codeanalysis;

import com.huawei.hms.ml.language.common.utils.Constant;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/codeanalysis/StaticValue.class
  classes12.dex
 */
/* JADX INFO: compiled from: GlobalAnalysisContext.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0004\u0003\u0004\u0005\u0006B\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0004\u0007\b\t\n¨\u0006\u000b"}, d2 = {"Lorg/catrobat/catroid/codeanalysis/StaticValue;", "", "()V", "Bool", "Dynamic", "Number", "Str", "Lorg/catrobat/catroid/codeanalysis/StaticValue$Bool;", "Lorg/catrobat/catroid/codeanalysis/StaticValue$Dynamic;", "Lorg/catrobat/catroid/codeanalysis/StaticValue$Number;", "Lorg/catrobat/catroid/codeanalysis/StaticValue$Str;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class StaticValue {
    public /* synthetic */ StaticValue(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/codeanalysis/StaticValue$Number.class
      classes12.dex
     */
    /* JADX INFO: compiled from: GlobalAnalysisContext.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0011"}, d2 = {"Lorg/catrobat/catroid/codeanalysis/StaticValue$Number;", "Lorg/catrobat/catroid/codeanalysis/StaticValue;", "value", "", "(D)V", "getValue", "()D", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class Number extends StaticValue {
        private final double value;

        public static /* synthetic */ Number copy$default(Number number, double d, int i, Object obj) {
            if ((i & 1) != 0) {
                d = number.value;
            }
            return number.copy(d);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final double getValue() {
            return this.value;
        }

        public final Number copy(double value) {
            return new Number(value);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            return (other instanceof Number) && Double.compare(this.value, ((Number) other).value) == 0;
        }

        public int hashCode() {
            return Double.hashCode(this.value);
        }

        public String toString() {
            return "Number(value=" + this.value + Constant.AFTER_QUTO;
        }

        public Number(double d) {
            super(null);
            this.value = d;
        }

        public final double getValue() {
            return this.value;
        }
    }

    private StaticValue() {
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/codeanalysis/StaticValue$Bool.class
      classes12.dex
     */
    /* JADX INFO: compiled from: GlobalAnalysisContext.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\u00032\b\u0010\n\u001a\u0004\u0018\u00010\u000bHÖ\u0003J\t\u0010\f\u001a\u00020\rHÖ\u0001J\t\u0010\u000e\u001a\u00020\u000fHÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0010"}, d2 = {"Lorg/catrobat/catroid/codeanalysis/StaticValue$Bool;", "Lorg/catrobat/catroid/codeanalysis/StaticValue;", "value", "", "(Z)V", "getValue", "()Z", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "", "hashCode", "", "toString", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class Bool extends StaticValue {
        private final boolean value;

        public static /* synthetic */ Bool copy$default(Bool bool, boolean z, int i, Object obj) {
            if ((i & 1) != 0) {
                z = bool.value;
            }
            return bool.copy(z);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final boolean getValue() {
            return this.value;
        }

        public final Bool copy(boolean value) {
            return new Bool(value);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            return (other instanceof Bool) && this.value == ((Bool) other).value;
        }

        public int hashCode() {
            return Boolean.hashCode(this.value);
        }

        public String toString() {
            return "Bool(value=" + this.value + Constant.AFTER_QUTO;
        }

        public Bool(boolean z) {
            super(null);
            this.value = z;
        }

        public final boolean getValue() {
            return this.value;
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/codeanalysis/StaticValue$Str.class
      classes12.dex
     */
    /* JADX INFO: compiled from: GlobalAnalysisContext.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\t\u0010\u000f\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0010"}, d2 = {"Lorg/catrobat/catroid/codeanalysis/StaticValue$Str;", "Lorg/catrobat/catroid/codeanalysis/StaticValue;", "value", "", "(Ljava/lang/String;)V", "getValue", "()Ljava/lang/String;", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class Str extends StaticValue {
        private final String value;

        public static /* synthetic */ Str copy$default(Str str, String str2, int i, Object obj) {
            if ((i & 1) != 0) {
                str2 = str.value;
            }
            return str.copy(str2);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final String getValue() {
            return this.value;
        }

        public final Str copy(String value) {
            Intrinsics.checkNotNullParameter(value, "value");
            return new Str(value);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            return (other instanceof Str) && Intrinsics.areEqual(this.value, ((Str) other).value);
        }

        public int hashCode() {
            return this.value.hashCode();
        }

        public String toString() {
            return "Str(value=" + this.value + Constant.AFTER_QUTO;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Str(String value) {
            super(null);
            Intrinsics.checkNotNullParameter(value, "value");
            this.value = value;
        }

        public final String getValue() {
            return this.value;
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/codeanalysis/StaticValue$Dynamic.class
      classes12.dex
     */
    /* JADX INFO: compiled from: GlobalAnalysisContext.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/catrobat/catroid/codeanalysis/StaticValue$Dynamic;", "Lorg/catrobat/catroid/codeanalysis/StaticValue;", "()V", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Dynamic extends StaticValue {
        public static final Dynamic INSTANCE = new Dynamic();

        private Dynamic() {
            super(null);
        }
    }
}
