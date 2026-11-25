package org.catrobat.catroid.ai;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ai/ParsedBrick.class
  classes9.dex
 */
/* JADX INFO: compiled from: KoveModelConverter.kt */
/* JADX INFO: loaded from: classes9.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0003\u0003\u0004\u0005B\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0003\u0006\u0007\b¨\u0006\t"}, d2 = {"Lorg/catrobat/catroid/ai/ParsedBrick;", "", "()V", "If", "Loop", "Simple", "Lorg/catrobat/catroid/ai/ParsedBrick$If;", "Lorg/catrobat/catroid/ai/ParsedBrick$Loop;", "Lorg/catrobat/catroid/ai/ParsedBrick$Simple;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ParsedBrick {
    public /* synthetic */ ParsedBrick(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private ParsedBrick() {
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ai/ParsedBrick$Simple.class
      classes9.dex
     */
    /* JADX INFO: compiled from: KoveModelConverter.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\u0015\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0003J)\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u0014\b\u0002\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012HÖ\u0003J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001J\t\u0010\u0015\u001a\u00020\u0003HÖ\u0001R\u001d\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0016"}, d2 = {"Lorg/catrobat/catroid/ai/ParsedBrick$Simple;", "Lorg/catrobat/catroid/ai/ParsedBrick;", "name", "", "arguments", "", "Lorg/catrobat/catroid/ai/ParsedFormulaElement;", "(Ljava/lang/String;Ljava/util/Map;)V", "getArguments", "()Ljava/util/Map;", "getName", "()Ljava/lang/String;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class Simple extends ParsedBrick {
        private final Map<String, ParsedFormulaElement> arguments;
        private final String name;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ Simple copy$default(Simple simple, String str, Map map, int i, Object obj) {
            if ((i & 1) != 0) {
                str = simple.name;
            }
            if ((i & 2) != 0) {
                map = simple.arguments;
            }
            return simple.copy(str, map);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final String getName() {
            return this.name;
        }

        public final Map<String, ParsedFormulaElement> component2() {
            return this.arguments;
        }

        public final Simple copy(String name2, Map<String, ParsedFormulaElement> arguments) {
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(arguments, "arguments");
            return new Simple(name2, arguments);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Simple)) {
                return false;
            }
            Simple simple = (Simple) other;
            return Intrinsics.areEqual(this.name, simple.name) && Intrinsics.areEqual(this.arguments, simple.arguments);
        }

        public int hashCode() {
            return (this.name.hashCode() * 31) + this.arguments.hashCode();
        }

        public String toString() {
            return "Simple(name=" + this.name + ", arguments=" + this.arguments + Constant.AFTER_QUTO;
        }

        public final String getName() {
            return this.name;
        }

        public final Map<String, ParsedFormulaElement> getArguments() {
            return this.arguments;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Simple(String name2, Map<String, ParsedFormulaElement> arguments) {
            super(null);
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(arguments, "arguments");
            this.name = name2;
            this.arguments = arguments;
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ai/ParsedBrick$If.class
      classes9.dex
     */
    /* JADX INFO: compiled from: KoveModelConverter.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\f\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B+\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005\u0012\u000e\u0010\u0006\u001a\n\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u0005¢\u0006\u0002\u0010\u0007J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\u000f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005HÆ\u0003J\u0011\u0010\u000f\u001a\n\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u0005HÆ\u0003J5\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u0010\b\u0002\u0010\u0006\u001a\n\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u0005HÆ\u0001J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014HÖ\u0003J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0019\u0010\u0006\u001a\n\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000b¨\u0006\u0019"}, d2 = {"Lorg/catrobat/catroid/ai/ParsedBrick$If;", "Lorg/catrobat/catroid/ai/ParsedBrick;", "condition", "Lorg/catrobat/catroid/ai/ParsedFormulaElement;", "thenBranch", "", "elseBranch", "(Lorg/catrobat/catroid/ai/ParsedFormulaElement;Ljava/util/List;Ljava/util/List;)V", "getCondition", "()Lorg/catrobat/catroid/ai/ParsedFormulaElement;", "getElseBranch", "()Ljava/util/List;", "getThenBranch", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class If extends ParsedBrick {
        private final ParsedFormulaElement condition;
        private final List<ParsedBrick> elseBranch;
        private final List<ParsedBrick> thenBranch;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ If copy$default(If r0, ParsedFormulaElement parsedFormulaElement, List list, List list2, int i, Object obj) {
            if ((i & 1) != 0) {
                parsedFormulaElement = r0.condition;
            }
            if ((i & 2) != 0) {
                list = r0.thenBranch;
            }
            if ((i & 4) != 0) {
                list2 = r0.elseBranch;
            }
            return r0.copy(parsedFormulaElement, list, list2);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final ParsedFormulaElement getCondition() {
            return this.condition;
        }

        public final List<ParsedBrick> component2() {
            return this.thenBranch;
        }

        public final List<ParsedBrick> component3() {
            return this.elseBranch;
        }

        public final If copy(ParsedFormulaElement condition, List<? extends ParsedBrick> thenBranch, List<? extends ParsedBrick> elseBranch) {
            Intrinsics.checkNotNullParameter(condition, "condition");
            Intrinsics.checkNotNullParameter(thenBranch, "thenBranch");
            return new If(condition, thenBranch, elseBranch);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof If)) {
                return false;
            }
            If r5 = (If) other;
            return Intrinsics.areEqual(this.condition, r5.condition) && Intrinsics.areEqual(this.thenBranch, r5.thenBranch) && Intrinsics.areEqual(this.elseBranch, r5.elseBranch);
        }

        public int hashCode() {
            int iHashCode = ((this.condition.hashCode() * 31) + this.thenBranch.hashCode()) * 31;
            List<ParsedBrick> list = this.elseBranch;
            return iHashCode + (list == null ? 0 : list.hashCode());
        }

        public String toString() {
            return "If(condition=" + this.condition + ", thenBranch=" + this.thenBranch + ", elseBranch=" + this.elseBranch + Constant.AFTER_QUTO;
        }

        public final ParsedFormulaElement getCondition() {
            return this.condition;
        }

        public final List<ParsedBrick> getThenBranch() {
            return this.thenBranch;
        }

        public final List<ParsedBrick> getElseBranch() {
            return this.elseBranch;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public If(ParsedFormulaElement condition, List<? extends ParsedBrick> thenBranch, List<? extends ParsedBrick> list) {
            super(null);
            Intrinsics.checkNotNullParameter(condition, "condition");
            Intrinsics.checkNotNullParameter(thenBranch, "thenBranch");
            this.condition = condition;
            this.thenBranch = thenBranch;
            this.elseBranch = list;
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ai/ParsedBrick$Loop.class
      classes9.dex
     */
    /* JADX INFO: compiled from: KoveModelConverter.kt */
    @Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\f\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B/\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00060\u0005\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\b¢\u0006\u0002\u0010\tJ\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J\u0015\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0003J\u000f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00010\bHÆ\u0003J9\u0010\u0013\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u0014\b\u0002\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00060\u00052\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\bHÆ\u0001J\u0013\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017HÖ\u0003J\t\u0010\u0018\u001a\u00020\u0019HÖ\u0001J\t\u0010\u001a\u001a\u00020\u0003HÖ\u0001R\u001d\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u001b"}, d2 = {"Lorg/catrobat/catroid/ai/ParsedBrick$Loop;", "Lorg/catrobat/catroid/ai/ParsedBrick;", "loopType", "", "arguments", "", "Lorg/catrobat/catroid/ai/ParsedFormulaElement;", "body", "", "(Ljava/lang/String;Ljava/util/Map;Ljava/util/List;)V", "getArguments", "()Ljava/util/Map;", "getBody", "()Ljava/util/List;", "getLoopType", "()Ljava/lang/String;", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class Loop extends ParsedBrick {
        private final Map<String, ParsedFormulaElement> arguments;
        private final List<ParsedBrick> body;
        private final String loopType;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ Loop copy$default(Loop loop, String str, Map map, List list, int i, Object obj) {
            if ((i & 1) != 0) {
                str = loop.loopType;
            }
            if ((i & 2) != 0) {
                map = loop.arguments;
            }
            if ((i & 4) != 0) {
                list = loop.body;
            }
            return loop.copy(str, map, list);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final String getLoopType() {
            return this.loopType;
        }

        public final Map<String, ParsedFormulaElement> component2() {
            return this.arguments;
        }

        public final List<ParsedBrick> component3() {
            return this.body;
        }

        public final Loop copy(String loopType, Map<String, ParsedFormulaElement> arguments, List<? extends ParsedBrick> body) {
            Intrinsics.checkNotNullParameter(loopType, "loopType");
            Intrinsics.checkNotNullParameter(arguments, "arguments");
            Intrinsics.checkNotNullParameter(body, "body");
            return new Loop(loopType, arguments, body);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Loop)) {
                return false;
            }
            Loop loop = (Loop) other;
            return Intrinsics.areEqual(this.loopType, loop.loopType) && Intrinsics.areEqual(this.arguments, loop.arguments) && Intrinsics.areEqual(this.body, loop.body);
        }

        public int hashCode() {
            return (((this.loopType.hashCode() * 31) + this.arguments.hashCode()) * 31) + this.body.hashCode();
        }

        public String toString() {
            return "Loop(loopType=" + this.loopType + ", arguments=" + this.arguments + ", body=" + this.body + Constant.AFTER_QUTO;
        }

        public final String getLoopType() {
            return this.loopType;
        }

        public final Map<String, ParsedFormulaElement> getArguments() {
            return this.arguments;
        }

        public final List<ParsedBrick> getBody() {
            return this.body;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public Loop(String loopType, Map<String, ParsedFormulaElement> arguments, List<? extends ParsedBrick> body) {
            super(null);
            Intrinsics.checkNotNullParameter(loopType, "loopType");
            Intrinsics.checkNotNullParameter(arguments, "arguments");
            Intrinsics.checkNotNullParameter(body, "body");
            this.loopType = loopType;
            this.arguments = arguments;
            this.body = body;
        }
    }
}
