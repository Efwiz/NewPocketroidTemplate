package org.catrobat.catroid.utils.lunoscript;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/lunoscript/SwitchCase.class
  classes14.dex
 */
/* JADX INFO: compiled from: AstNodes.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0010\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B/\u0012\u000e\u0010\u0002\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\u0011\u0010\u0013\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0006HÆ\u0003J\t\u0010\u0015\u001a\u00020\bHÆ\u0003J\t\u0010\u0016\u001a\u00020\nHÆ\u0003J9\u0010\u0017\u001a\u00020\u00002\u0010\b\u0002\u0010\u0002\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\nHÆ\u0001J\u0013\u0010\u0018\u001a\u00020\n2\b\u0010\u0019\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001a\u001a\u00020\u001bHÖ\u0001J\t\u0010\u001c\u001a\u00020\u001dHÖ\u0001R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0010R\u0019\u0010\u0002\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u001e"}, d2 = {"Lorg/catrobat/catroid/utils/lunoscript/SwitchCase;", "", "valueExpressions", "", "Lorg/catrobat/catroid/utils/lunoscript/Expression;", "body", "Lorg/catrobat/catroid/utils/lunoscript/Statement;", "caseOrDefaultToken", "Lorg/catrobat/catroid/utils/lunoscript/Token;", "isDefault", "", "(Ljava/util/List;Lorg/catrobat/catroid/utils/lunoscript/Statement;Lorg/catrobat/catroid/utils/lunoscript/Token;Z)V", "getBody", "()Lorg/catrobat/catroid/utils/lunoscript/Statement;", "getCaseOrDefaultToken", "()Lorg/catrobat/catroid/utils/lunoscript/Token;", "()Z", "getValueExpressions", "()Ljava/util/List;", "component1", "component2", "component3", "component4", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "", "toString", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class SwitchCase {
    private final Statement body;
    private final Token caseOrDefaultToken;
    private final boolean isDefault;
    private final List<Expression> valueExpressions;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ SwitchCase copy$default(SwitchCase switchCase, List list, Statement statement, Token token, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            list = switchCase.valueExpressions;
        }
        if ((i & 2) != 0) {
            statement = switchCase.body;
        }
        if ((i & 4) != 0) {
            token = switchCase.caseOrDefaultToken;
        }
        if ((i & 8) != 0) {
            z = switchCase.isDefault;
        }
        return switchCase.copy(list, statement, token, z);
    }

    public final List<Expression> component1() {
        return this.valueExpressions;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final Statement getBody() {
        return this.body;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final Token getCaseOrDefaultToken() {
        return this.caseOrDefaultToken;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final boolean getIsDefault() {
        return this.isDefault;
    }

    public final SwitchCase copy(List<? extends Expression> valueExpressions, Statement body, Token caseOrDefaultToken, boolean isDefault) {
        Intrinsics.checkNotNullParameter(body, "body");
        Intrinsics.checkNotNullParameter(caseOrDefaultToken, "caseOrDefaultToken");
        return new SwitchCase(valueExpressions, body, caseOrDefaultToken, isDefault);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SwitchCase)) {
            return false;
        }
        SwitchCase switchCase = (SwitchCase) other;
        return Intrinsics.areEqual(this.valueExpressions, switchCase.valueExpressions) && Intrinsics.areEqual(this.body, switchCase.body) && Intrinsics.areEqual(this.caseOrDefaultToken, switchCase.caseOrDefaultToken) && this.isDefault == switchCase.isDefault;
    }

    public int hashCode() {
        List<Expression> list = this.valueExpressions;
        return ((((((list == null ? 0 : list.hashCode()) * 31) + this.body.hashCode()) * 31) + this.caseOrDefaultToken.hashCode()) * 31) + Boolean.hashCode(this.isDefault);
    }

    public String toString() {
        return "SwitchCase(valueExpressions=" + this.valueExpressions + ", body=" + this.body + ", caseOrDefaultToken=" + this.caseOrDefaultToken + ", isDefault=" + this.isDefault + Constant.AFTER_QUTO;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public SwitchCase(List<? extends Expression> list, Statement body, Token caseOrDefaultToken, boolean z) {
        Intrinsics.checkNotNullParameter(body, "body");
        Intrinsics.checkNotNullParameter(caseOrDefaultToken, "caseOrDefaultToken");
        this.valueExpressions = list;
        this.body = body;
        this.caseOrDefaultToken = caseOrDefaultToken;
        this.isDefault = z;
    }

    public /* synthetic */ SwitchCase(List list, Statement statement, Token token, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(list, statement, token, (i & 8) != 0 ? false : z);
    }

    public final Statement getBody() {
        return this.body;
    }

    public final Token getCaseOrDefaultToken() {
        return this.caseOrDefaultToken;
    }

    public final List<Expression> getValueExpressions() {
        return this.valueExpressions;
    }

    public final boolean isDefault() {
        return this.isDefault;
    }
}
