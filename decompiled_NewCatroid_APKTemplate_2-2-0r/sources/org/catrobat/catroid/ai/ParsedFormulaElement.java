package org.catrobat.catroid.ai;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ai/ParsedFormulaElement.class
  classes9.dex
 */
/* JADX INFO: compiled from: KoveModelConverter.kt */
/* JADX INFO: loaded from: classes9.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0011\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B=\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0000\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0000\u0012\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00000\t¢\u0006\u0002\u0010\nJ\t\u0010\u0014\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0015\u001a\u00020\u0005HÆ\u0003J\u000b\u0010\u0016\u001a\u0004\u0018\u00010\u0000HÆ\u0003J\u000b\u0010\u0017\u001a\u0004\u0018\u00010\u0000HÆ\u0003J\u000f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00000\tHÆ\u0003JE\u0010\u0019\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00002\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00002\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00000\tHÆ\u0001J\u0013\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001d\u001a\u00020\u001eHÖ\u0001J\t\u0010\u001f\u001a\u00020\u0005HÖ\u0001R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00000\t¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0000¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0000¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000eR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013¨\u0006 "}, d2 = {"Lorg/catrobat/catroid/ai/ParsedFormulaElement;", "", ModuleXmlParser.TYPE, "Lorg/catrobat/catroid/ai/FormulaElementType;", "value", "", "leftChild", "rightChild", "additionalChildren", "", "(Lorg/catrobat/catroid/ai/FormulaElementType;Ljava/lang/String;Lorg/catrobat/catroid/ai/ParsedFormulaElement;Lorg/catrobat/catroid/ai/ParsedFormulaElement;Ljava/util/List;)V", "getAdditionalChildren", "()Ljava/util/List;", "getLeftChild", "()Lorg/catrobat/catroid/ai/ParsedFormulaElement;", "getRightChild", "getType", "()Lorg/catrobat/catroid/ai/FormulaElementType;", "getValue", "()Ljava/lang/String;", "component1", "component2", "component3", "component4", "component5", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ParsedFormulaElement {
    private final List<ParsedFormulaElement> additionalChildren;
    private final ParsedFormulaElement leftChild;
    private final ParsedFormulaElement rightChild;
    private final FormulaElementType type;
    private final String value;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ ParsedFormulaElement copy$default(ParsedFormulaElement parsedFormulaElement, FormulaElementType formulaElementType, String str, ParsedFormulaElement parsedFormulaElement2, ParsedFormulaElement parsedFormulaElement3, List list, int i, Object obj) {
        if ((i & 1) != 0) {
            formulaElementType = parsedFormulaElement.type;
        }
        if ((i & 2) != 0) {
            str = parsedFormulaElement.value;
        }
        String str2 = str;
        if ((i & 4) != 0) {
            parsedFormulaElement2 = parsedFormulaElement.leftChild;
        }
        ParsedFormulaElement parsedFormulaElement4 = parsedFormulaElement2;
        if ((i & 8) != 0) {
            parsedFormulaElement3 = parsedFormulaElement.rightChild;
        }
        ParsedFormulaElement parsedFormulaElement5 = parsedFormulaElement3;
        if ((i & 16) != 0) {
            list = parsedFormulaElement.additionalChildren;
        }
        return parsedFormulaElement.copy(formulaElementType, str2, parsedFormulaElement4, parsedFormulaElement5, list);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final FormulaElementType getType() {
        return this.type;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getValue() {
        return this.value;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final ParsedFormulaElement getLeftChild() {
        return this.leftChild;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final ParsedFormulaElement getRightChild() {
        return this.rightChild;
    }

    public final List<ParsedFormulaElement> component5() {
        return this.additionalChildren;
    }

    public final ParsedFormulaElement copy(FormulaElementType type, String value, ParsedFormulaElement leftChild, ParsedFormulaElement rightChild, List<ParsedFormulaElement> additionalChildren) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(additionalChildren, "additionalChildren");
        return new ParsedFormulaElement(type, value, leftChild, rightChild, additionalChildren);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ParsedFormulaElement)) {
            return false;
        }
        ParsedFormulaElement parsedFormulaElement = (ParsedFormulaElement) other;
        return this.type == parsedFormulaElement.type && Intrinsics.areEqual(this.value, parsedFormulaElement.value) && Intrinsics.areEqual(this.leftChild, parsedFormulaElement.leftChild) && Intrinsics.areEqual(this.rightChild, parsedFormulaElement.rightChild) && Intrinsics.areEqual(this.additionalChildren, parsedFormulaElement.additionalChildren);
    }

    public int hashCode() {
        int iHashCode = ((this.type.hashCode() * 31) + this.value.hashCode()) * 31;
        ParsedFormulaElement parsedFormulaElement = this.leftChild;
        int iHashCode2 = (iHashCode + (parsedFormulaElement == null ? 0 : parsedFormulaElement.hashCode())) * 31;
        ParsedFormulaElement parsedFormulaElement2 = this.rightChild;
        return ((iHashCode2 + (parsedFormulaElement2 != null ? parsedFormulaElement2.hashCode() : 0)) * 31) + this.additionalChildren.hashCode();
    }

    public String toString() {
        return "ParsedFormulaElement(type=" + this.type + ", value=" + this.value + ", leftChild=" + this.leftChild + ", rightChild=" + this.rightChild + ", additionalChildren=" + this.additionalChildren + Constant.AFTER_QUTO;
    }

    public ParsedFormulaElement(FormulaElementType type, String value, ParsedFormulaElement parsedFormulaElement, ParsedFormulaElement parsedFormulaElement2, List<ParsedFormulaElement> additionalChildren) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(additionalChildren, "additionalChildren");
        this.type = type;
        this.value = value;
        this.leftChild = parsedFormulaElement;
        this.rightChild = parsedFormulaElement2;
        this.additionalChildren = additionalChildren;
    }

    public final FormulaElementType getType() {
        return this.type;
    }

    public final String getValue() {
        return this.value;
    }

    public final ParsedFormulaElement getLeftChild() {
        return this.leftChild;
    }

    public final ParsedFormulaElement getRightChild() {
        return this.rightChild;
    }

    public /* synthetic */ ParsedFormulaElement(FormulaElementType formulaElementType, String str, ParsedFormulaElement parsedFormulaElement, ParsedFormulaElement parsedFormulaElement2, List list, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(formulaElementType, str, (i & 4) != 0 ? null : parsedFormulaElement, (i & 8) != 0 ? null : parsedFormulaElement2, (i & 16) != 0 ? CollectionsKt.emptyList() : list);
    }

    public final List<ParsedFormulaElement> getAdditionalChildren() {
        return this.additionalChildren;
    }
}
