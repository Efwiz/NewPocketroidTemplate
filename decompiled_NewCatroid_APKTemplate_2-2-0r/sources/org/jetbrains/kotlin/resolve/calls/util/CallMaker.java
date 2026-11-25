package org.jetbrains.kotlin.resolve.calls.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.jetbrains.kotlin.com.google.common.collect.Lists;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafPsiElement;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtArrayAccessExpression;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.psi.KtCallElement;
import org.jetbrains.kotlin.psi.KtCollectionLiteralExpression;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtLambdaArgument;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.KtSuperTypeCallEntry;
import org.jetbrains.kotlin.psi.KtTypeArgumentList;
import org.jetbrains.kotlin.psi.KtTypeProjection;
import org.jetbrains.kotlin.psi.KtUnaryExpression;
import org.jetbrains.kotlin.psi.KtValueArgumentList;
import org.jetbrains.kotlin.psi.LambdaArgument;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.psi.ValueArgumentName;
import org.jetbrains.kotlin.psi.debugText.DebugTextUtilKt;
import org.jetbrains.kotlin.resolve.scopes.receivers.Receiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;

/* JADX INFO: loaded from: classes6.dex */
public class CallMaker {
    /* JADX WARN: Code duplicated, block: B:25:0x0051  */
    /* JADX WARN: Code duplicated, block: B:26:0x0056  */
    /* JADX WARN: Code duplicated, block: B:27:0x005b  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "calleeExpression";
        } else if (i == 2) {
            objArr[0] = "argumentExpressions";
        } else if (i == 4) {
            objArr[0] = "calleeExpression";
        } else if (i == 5) {
            objArr[0] = "argumentExpressions";
        } else if (i == 6) {
            objArr[0] = "callType";
        } else if (i != 31) {
            switch (i) {
                case 8:
                    objArr[0] = "calleeExpression";
                    break;
                case 9:
                    objArr[0] = "argumentExpressions";
                    break;
                case 10:
                case 16:
                case 19:
                    objArr[0] = "callType";
                    break;
                case 11:
                    objArr[0] = "leftAsReceiver";
                    break;
                case 12:
                    objArr[0] = "baseAsReceiver";
                    break;
                case 13:
                case 17:
                    objArr[0] = "arrayAsReceiver";
                    break;
                case 14:
                case 18:
                    objArr[0] = "arrayAccessExpression";
                    break;
                case 15:
                    objArr[0] = "rightHandSide";
                    break;
                case 20:
                    objArr[0] = "collectionLiteralExpression";
                    break;
                case 21:
                case 23:
                case 24:
                    objArr[0] = "expression";
                    break;
                case 22:
                case 25:
                    objArr[0] = "reportErrorsOn";
                    break;
                case 26:
                    objArr[0] = "nameExpression";
                    break;
                default:
                    objArr[0] = "callElement";
                    break;
            }
        } else {
            objArr[0] = "explicitReceiver";
        }
        objArr[1] = "org/jetbrains/kotlin/resolve/calls/util/CallMaker";
        switch (i) {
            case 11:
            case 12:
            case 29:
            case 30:
            case 31:
                objArr[2] = "makeCall";
                break;
            case 13:
            case 14:
            case 15:
            case 16:
                objArr[2] = "makeArraySetCall";
                break;
            case 17:
            case 18:
            case 19:
                objArr[2] = "makeArrayGetCall";
                break;
            case 20:
                objArr[2] = "makeCallForCollectionLiteral";
                break;
            case 21:
            case 22:
                objArr[2] = "makeValueArgument";
                break;
            case 23:
            case 24:
            case 25:
                objArr[2] = "makeExternalValueArgument";
                break;
            case 26:
                objArr[2] = "makePropertyCall";
                break;
            case 27:
                objArr[2] = "makeConstructorCallWithoutTypeArguments";
                break;
            case 28:
                objArr[2] = "makeConstructorCallForEnumEntryWithoutInitializer";
                break;
            default:
                objArr[2] = "makeCallWithExpressions";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    private static class ExpressionValueArgument implements ValueArgument {
        private final KtExpression expression;
        private final boolean isExternal;
        private final KtElement reportErrorsOn;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 1 ? 3 : 2];
            if (i != 1) {
                objArr[0] = "reportErrorsOn";
            } else {
                objArr[0] = "org/jetbrains/kotlin/resolve/calls/util/CallMaker$ExpressionValueArgument";
            }
            if (i != 1) {
                objArr[1] = "org/jetbrains/kotlin/resolve/calls/util/CallMaker$ExpressionValueArgument";
            } else {
                objArr[1] = "asElement";
            }
            if (i != 1) {
                objArr[2] = "<init>";
            }
            String str2 = String.format(str, objArr);
            if (i == 1) {
                throw new IllegalStateException(str2);
            }
        }

        @Override // org.jetbrains.kotlin.psi.ValueArgument
        public ValueArgumentName getArgumentName() {
            return null;
        }

        @Override // org.jetbrains.kotlin.psi.ValueArgument
        public LeafPsiElement getSpreadElement() {
            return null;
        }

        @Override // org.jetbrains.kotlin.psi.ValueArgument
        public boolean isNamed() {
            return false;
        }

        private ExpressionValueArgument(KtExpression ktExpression, KtElement ktElement, boolean z) {
            if (ktElement == null) {
                $$$reportNull$$$0(0);
            }
            this.expression = ktExpression;
            this.reportErrorsOn = ktExpression == null ? ktElement : ktExpression;
            this.isExternal = z;
        }

        @Override // org.jetbrains.kotlin.psi.ValueArgument
        public boolean isExternal() {
            return this.isExternal;
        }

        @Override // org.jetbrains.kotlin.psi.ValueArgument
        public KtExpression getArgumentExpression() {
            return this.expression;
        }

        @Override // org.jetbrains.kotlin.psi.ValueArgument
        /* JADX INFO: renamed from: asElement */
        public KtElement getCallElement() {
            KtElement ktElement = this.reportErrorsOn;
            if (ktElement == null) {
                $$$reportNull$$$0(1);
            }
            return ktElement;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            KtExpression ktExpression = this.expression;
            KtExpression ktExpression2 = ((ExpressionValueArgument) obj).expression;
            return ktExpression == null ? ktExpression2 == null : ktExpression.equals(ktExpression2);
        }

        public int hashCode() {
            KtExpression ktExpression = this.expression;
            if (ktExpression != null) {
                return ktExpression.hashCode();
            }
            return 0;
        }
    }

    private static class CallImpl implements Call {
        private final KtElement callElement;
        private final ASTNode callOperationNode;
        private final Call.CallType callType;
        private final KtExpression calleeExpression;
        private final Receiver explicitReceiver;
        private final boolean isSemanticallyEquivalentToSafeCall;
        private final List<? extends ValueArgument> valueArguments;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str;
            int i2;
            switch (i) {
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
            switch (i) {
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                    i2 = 2;
                    break;
                default:
                    i2 = 3;
                    break;
            }
            Object[] objArr = new Object[i2];
            switch (i) {
                case 1:
                    objArr[0] = "explicitReceiver";
                    break;
                case 2:
                case 4:
                    objArr[0] = "valueArguments";
                    break;
                case 3:
                default:
                    objArr[0] = "callElement";
                    break;
                case 5:
                    objArr[0] = "callType";
                    break;
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                    objArr[0] = "org/jetbrains/kotlin/resolve/calls/util/CallMaker$CallImpl";
                    break;
            }
            switch (i) {
                case 6:
                    objArr[1] = "getValueArguments";
                    break;
                case 7:
                    objArr[1] = "getCallElement";
                    break;
                case 8:
                    objArr[1] = "getFunctionLiteralArguments";
                    break;
                case 9:
                    objArr[1] = "getTypeArguments";
                    break;
                case 10:
                    objArr[1] = "getCallType";
                    break;
                default:
                    objArr[1] = "org/jetbrains/kotlin/resolve/calls/util/CallMaker$CallImpl";
                    break;
            }
            switch (i) {
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                    break;
                default:
                    objArr[2] = "<init>";
                    break;
            }
            String str2 = String.format(str, objArr);
            switch (i) {
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                    throw new IllegalStateException(str2);
                default:
                    throw new IllegalArgumentException(str2);
            }
        }

        @Override // org.jetbrains.kotlin.psi.Call
        public ReceiverValue getDispatchReceiver() {
            return null;
        }

        @Override // org.jetbrains.kotlin.psi.Call
        public KtTypeArgumentList getTypeArgumentList() {
            return null;
        }

        @Override // org.jetbrains.kotlin.psi.Call
        public KtValueArgumentList getValueArgumentList() {
            return null;
        }

        /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
        protected CallImpl(KtElement ktElement, Receiver receiver, ASTNode aSTNode, KtExpression ktExpression, List<? extends ValueArgument> list) {
            this(ktElement, receiver, aSTNode, ktExpression, list, Call.CallType.DEFAULT, false);
            if (ktElement == null) {
                $$$reportNull$$$0(0);
            }
            if (receiver == null) {
                $$$reportNull$$$0(1);
            }
            if (list == null) {
                $$$reportNull$$$0(2);
            }
        }

        protected CallImpl(KtElement ktElement, Receiver receiver, ASTNode aSTNode, KtExpression ktExpression, List<? extends ValueArgument> list, Call.CallType callType, boolean z) {
            if (ktElement == null) {
                $$$reportNull$$$0(3);
            }
            if (list == null) {
                $$$reportNull$$$0(4);
            }
            if (callType == null) {
                $$$reportNull$$$0(5);
            }
            this.callElement = ktElement;
            this.explicitReceiver = receiver;
            this.callOperationNode = aSTNode;
            this.calleeExpression = ktExpression;
            this.valueArguments = list;
            this.callType = callType;
            this.isSemanticallyEquivalentToSafeCall = z;
        }

        @Override // org.jetbrains.kotlin.psi.Call
        public ASTNode getCallOperationNode() {
            return this.callOperationNode;
        }

        @Override // org.jetbrains.kotlin.psi.Call
        public boolean isSemanticallyEquivalentToSafeCall() {
            return this.isSemanticallyEquivalentToSafeCall || super.isSemanticallyEquivalentToSafeCall();
        }

        @Override // org.jetbrains.kotlin.psi.Call
        public Receiver getExplicitReceiver() {
            return this.explicitReceiver;
        }

        @Override // org.jetbrains.kotlin.psi.Call
        public KtExpression getCalleeExpression() {
            return this.calleeExpression;
        }

        @Override // org.jetbrains.kotlin.psi.Call
        public List<? extends ValueArgument> getValueArguments() {
            List<? extends ValueArgument> list = this.valueArguments;
            if (list == null) {
                $$$reportNull$$$0(6);
            }
            return list;
        }

        @Override // org.jetbrains.kotlin.psi.Call
        public KtElement getCallElement() {
            KtElement ktElement = this.callElement;
            if (ktElement == null) {
                $$$reportNull$$$0(7);
            }
            return ktElement;
        }

        @Override // org.jetbrains.kotlin.psi.Call
        public List<LambdaArgument> getFunctionLiteralArguments() {
            List<LambdaArgument> listEmptyList = Collections.emptyList();
            if (listEmptyList == null) {
                $$$reportNull$$$0(8);
            }
            return listEmptyList;
        }

        @Override // org.jetbrains.kotlin.psi.Call
        public List<KtTypeProjection> getTypeArguments() {
            List<KtTypeProjection> listEmptyList = Collections.emptyList();
            if (listEmptyList == null) {
                $$$reportNull$$$0(9);
            }
            return listEmptyList;
        }

        public String toString() {
            return getCallElement().getText();
        }

        @Override // org.jetbrains.kotlin.psi.Call
        public Call.CallType getCallType() {
            Call.CallType callType = this.callType;
            if (callType == null) {
                $$$reportNull$$$0(10);
            }
            return callType;
        }
    }

    public static Call makeCallWithExpressions(KtElement ktElement, Receiver receiver, ASTNode aSTNode, KtExpression ktExpression, List<KtExpression> list) {
        if (ktElement == null) {
            $$$reportNull$$$0(0);
        }
        if (ktExpression == null) {
            $$$reportNull$$$0(1);
        }
        if (list == null) {
            $$$reportNull$$$0(2);
        }
        return makeCallWithExpressions(ktElement, receiver, aSTNode, ktExpression, list, Call.CallType.DEFAULT, false);
    }

    public static Call makeCallWithExpressions(KtElement ktElement, Receiver receiver, ASTNode aSTNode, KtExpression ktExpression, List<KtExpression> list, Call.CallType callType) {
        if (ktElement == null) {
            $$$reportNull$$$0(3);
        }
        if (ktExpression == null) {
            $$$reportNull$$$0(4);
        }
        if (list == null) {
            $$$reportNull$$$0(5);
        }
        if (callType == null) {
            $$$reportNull$$$0(6);
        }
        return makeCallWithExpressions(ktElement, receiver, aSTNode, ktExpression, list, callType, false);
    }

    public static Call makeCallWithExpressions(KtElement ktElement, Receiver receiver, ASTNode aSTNode, KtExpression ktExpression, List<KtExpression> list, Call.CallType callType, boolean z) {
        List listEmptyList;
        if (ktElement == null) {
            $$$reportNull$$$0(7);
        }
        if (ktExpression == null) {
            $$$reportNull$$$0(8);
        }
        if (list == null) {
            $$$reportNull$$$0(9);
        }
        if (callType == null) {
            $$$reportNull$$$0(10);
        }
        if (list.isEmpty()) {
            listEmptyList = Collections.emptyList();
        } else {
            ArrayList arrayList = new ArrayList(list.size());
            Iterator<KtExpression> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(makeValueArgument(it.next(), ktExpression));
            }
            listEmptyList = arrayList;
        }
        return makeCall(ktElement, receiver, aSTNode, ktExpression, listEmptyList, callType, z);
    }

    public static Call makeCall(KtElement ktElement, Receiver receiver, ASTNode aSTNode, KtExpression ktExpression, List<? extends ValueArgument> list) {
        return makeCall(ktElement, receiver, aSTNode, ktExpression, list, Call.CallType.DEFAULT);
    }

    public static Call makeCall(KtElement ktElement, Receiver receiver, ASTNode aSTNode, KtExpression ktExpression, List<? extends ValueArgument> list, Call.CallType callType) {
        return makeCall(ktElement, receiver, aSTNode, ktExpression, list, callType, false);
    }

    public static Call makeCall(KtElement ktElement, Receiver receiver, ASTNode aSTNode, KtExpression ktExpression, List<? extends ValueArgument> list, Call.CallType callType, boolean z) {
        return new CallImpl(ktElement, receiver, aSTNode, ktExpression, list, callType, z);
    }

    public static Call makeCall(ReceiverValue receiverValue, KtBinaryExpression ktBinaryExpression) {
        if (receiverValue == null) {
            $$$reportNull$$$0(11);
        }
        return makeCallWithExpressions(ktBinaryExpression, receiverValue, null, ktBinaryExpression.getOperationReference(), Collections.singletonList(ktBinaryExpression.getRight()));
    }

    public static Call makeCall(ReceiverValue receiverValue, KtUnaryExpression ktUnaryExpression) {
        if (receiverValue == null) {
            $$$reportNull$$$0(12);
        }
        return makeCall(ktUnaryExpression, receiverValue, null, ktUnaryExpression.getOperationReference(), Collections.emptyList());
    }

    public static Call makeArraySetCall(ReceiverValue receiverValue, KtArrayAccessExpression ktArrayAccessExpression, KtExpression ktExpression, Call.CallType callType) {
        if (receiverValue == null) {
            $$$reportNull$$$0(13);
        }
        if (ktArrayAccessExpression == null) {
            $$$reportNull$$$0(14);
        }
        if (ktExpression == null) {
            $$$reportNull$$$0(15);
        }
        if (callType == null) {
            $$$reportNull$$$0(16);
        }
        ArrayList arrayListNewArrayList = Lists.newArrayList(ktArrayAccessExpression.getIndexExpressions());
        arrayListNewArrayList.add(ktExpression);
        return makeCallWithExpressions(ktArrayAccessExpression, receiverValue, null, ktArrayAccessExpression, arrayListNewArrayList, callType);
    }

    public static Call makeArrayGetCall(ReceiverValue receiverValue, KtArrayAccessExpression ktArrayAccessExpression, Call.CallType callType) {
        if (receiverValue == null) {
            $$$reportNull$$$0(17);
        }
        if (ktArrayAccessExpression == null) {
            $$$reportNull$$$0(18);
        }
        if (callType == null) {
            $$$reportNull$$$0(19);
        }
        return makeCallWithExpressions(ktArrayAccessExpression, receiverValue, null, ktArrayAccessExpression, ktArrayAccessExpression.getIndexExpressions(), callType);
    }

    public static Call makeCallForCollectionLiteral(KtCollectionLiteralExpression ktCollectionLiteralExpression) {
        if (ktCollectionLiteralExpression == null) {
            $$$reportNull$$$0(20);
        }
        return makeCallWithExpressions(ktCollectionLiteralExpression, null, null, ktCollectionLiteralExpression, ktCollectionLiteralExpression.getInnerExpressions(), Call.CallType.DEFAULT);
    }

    public static ValueArgument makeValueArgument(KtExpression ktExpression) {
        if (ktExpression == null) {
            $$$reportNull$$$0(21);
        }
        return makeValueArgument(ktExpression, ktExpression);
    }

    public static ValueArgument makeValueArgument(KtExpression ktExpression, KtElement ktElement) {
        if (ktElement == null) {
            $$$reportNull$$$0(22);
        }
        return new ExpressionValueArgument(ktExpression, ktElement, false);
    }

    public static ValueArgument makeExternalValueArgument(KtExpression ktExpression) {
        if (ktExpression == null) {
            $$$reportNull$$$0(23);
        }
        return new ExpressionValueArgument(ktExpression, ktExpression, true);
    }

    public static ValueArgument makeExternalValueArgument(KtExpression ktExpression, KtElement ktElement) {
        if (ktExpression == null) {
            $$$reportNull$$$0(24);
        }
        if (ktElement == null) {
            $$$reportNull$$$0(25);
        }
        return new ExpressionValueArgument(ktExpression, ktElement, true);
    }

    public static Call makePropertyCall(Receiver receiver, ASTNode aSTNode, KtSimpleNameExpression ktSimpleNameExpression) {
        if (ktSimpleNameExpression == null) {
            $$$reportNull$$$0(26);
        }
        return makeCallWithExpressions(ktSimpleNameExpression, receiver, aSTNode, ktSimpleNameExpression, Collections.emptyList());
    }

    public static Call makeConstructorCallWithoutTypeArguments(KtCallElement ktCallElement) {
        if (ktCallElement == null) {
            $$$reportNull$$$0(27);
        }
        return new DelegatingCall(makeCall(null, null, ktCallElement)) { // from class: org.jetbrains.kotlin.resolve.calls.util.CallMaker.1
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/resolve/calls/util/CallMaker$1", "getTypeArguments"));
            }

            @Override // org.jetbrains.kotlin.resolve.calls.util.DelegatingCall, org.jetbrains.kotlin.psi.Call
            public KtTypeArgumentList getTypeArgumentList() {
                return null;
            }

            @Override // org.jetbrains.kotlin.resolve.calls.util.DelegatingCall, org.jetbrains.kotlin.psi.Call
            public List<KtTypeProjection> getTypeArguments() {
                List<KtTypeProjection> listEmptyList = Collections.emptyList();
                if (listEmptyList == null) {
                    $$$reportNull$$$0(0);
                }
                return listEmptyList;
            }
        };
    }

    public static Call makeConstructorCallForEnumEntryWithoutInitializer(final KtSuperTypeCallEntry ktSuperTypeCallEntry) {
        if (ktSuperTypeCallEntry == null) {
            $$$reportNull$$$0(28);
        }
        return new Call() { // from class: org.jetbrains.kotlin.resolve.calls.util.CallMaker.2
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                Object[] objArr = new Object[2];
                objArr[0] = "org/jetbrains/kotlin/resolve/calls/util/CallMaker$2";
                if (i == 1) {
                    objArr[1] = "getFunctionLiteralArguments";
                } else if (i == 2) {
                    objArr[1] = "getTypeArguments";
                } else if (i == 3) {
                    objArr[1] = "getCallElement";
                } else if (i != 4) {
                    objArr[1] = "getValueArguments";
                } else {
                    objArr[1] = "getCallType";
                }
                throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", objArr));
            }

            @Override // org.jetbrains.kotlin.psi.Call
            public ASTNode getCallOperationNode() {
                return null;
            }

            @Override // org.jetbrains.kotlin.psi.Call
            public ReceiverValue getDispatchReceiver() {
                return null;
            }

            @Override // org.jetbrains.kotlin.psi.Call
            public Receiver getExplicitReceiver() {
                return null;
            }

            @Override // org.jetbrains.kotlin.psi.Call
            public KtTypeArgumentList getTypeArgumentList() {
                return null;
            }

            @Override // org.jetbrains.kotlin.psi.Call
            public KtExpression getCalleeExpression() {
                return ktSuperTypeCallEntry.getCalleeExpression();
            }

            @Override // org.jetbrains.kotlin.psi.Call
            public KtValueArgumentList getValueArgumentList() {
                return ktSuperTypeCallEntry.getValueArgumentList();
            }

            @Override // org.jetbrains.kotlin.psi.Call
            public List<? extends ValueArgument> getValueArguments() {
                List<? extends ValueArgument> valueArguments = ktSuperTypeCallEntry.getValueArguments();
                if (valueArguments == null) {
                    $$$reportNull$$$0(0);
                }
                return valueArguments;
            }

            @Override // org.jetbrains.kotlin.psi.Call
            public List<? extends LambdaArgument> getFunctionLiteralArguments() {
                List<? extends LambdaArgument> listEmptyList = Collections.emptyList();
                if (listEmptyList == null) {
                    $$$reportNull$$$0(1);
                }
                return listEmptyList;
            }

            @Override // org.jetbrains.kotlin.psi.Call
            public List<KtTypeProjection> getTypeArguments() {
                List<KtTypeProjection> listEmptyList = Collections.emptyList();
                if (listEmptyList == null) {
                    $$$reportNull$$$0(2);
                }
                return listEmptyList;
            }

            @Override // org.jetbrains.kotlin.psi.Call
            public KtElement getCallElement() {
                KtSuperTypeCallEntry ktSuperTypeCallEntry2 = ktSuperTypeCallEntry;
                if (ktSuperTypeCallEntry2 == null) {
                    $$$reportNull$$$0(3);
                }
                return ktSuperTypeCallEntry2;
            }

            @Override // org.jetbrains.kotlin.psi.Call
            public Call.CallType getCallType() {
                Call.CallType callType = Call.CallType.DEFAULT;
                if (callType == null) {
                    $$$reportNull$$$0(4);
                }
                return callType;
            }

            public String toString() {
                return DebugTextUtilKt.getDebugText(ktSuperTypeCallEntry);
            }
        };
    }

    public static Call makeCall(final Receiver receiver, final ASTNode aSTNode, final KtCallElement ktCallElement) {
        if (ktCallElement == null) {
            $$$reportNull$$$0(29);
        }
        return new Call() { // from class: org.jetbrains.kotlin.resolve.calls.util.CallMaker.3
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                Object[] objArr = new Object[2];
                objArr[0] = "org/jetbrains/kotlin/resolve/calls/util/CallMaker$3";
                if (i == 1) {
                    objArr[1] = "getFunctionLiteralArguments";
                } else if (i == 2) {
                    objArr[1] = "getTypeArguments";
                } else if (i == 3) {
                    objArr[1] = "getCallElement";
                } else if (i != 4) {
                    objArr[1] = "getValueArguments";
                } else {
                    objArr[1] = "getCallType";
                }
                throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", objArr));
            }

            @Override // org.jetbrains.kotlin.psi.Call
            public ReceiverValue getDispatchReceiver() {
                return null;
            }

            @Override // org.jetbrains.kotlin.psi.Call
            public ASTNode getCallOperationNode() {
                return aSTNode;
            }

            @Override // org.jetbrains.kotlin.psi.Call
            public Receiver getExplicitReceiver() {
                return receiver;
            }

            @Override // org.jetbrains.kotlin.psi.Call
            public KtExpression getCalleeExpression() {
                return ktCallElement.getCalleeExpression();
            }

            @Override // org.jetbrains.kotlin.psi.Call
            public KtValueArgumentList getValueArgumentList() {
                return ktCallElement.getValueArgumentList();
            }

            @Override // org.jetbrains.kotlin.psi.Call
            public List<? extends ValueArgument> getValueArguments() {
                List<? extends ValueArgument> valueArguments = ktCallElement.getValueArguments();
                if (valueArguments == null) {
                    $$$reportNull$$$0(0);
                }
                return valueArguments;
            }

            @Override // org.jetbrains.kotlin.psi.Call
            public List<? extends LambdaArgument> getFunctionLiteralArguments() {
                List<KtLambdaArgument> lambdaArguments = ktCallElement.getLambdaArguments();
                if (lambdaArguments == null) {
                    $$$reportNull$$$0(1);
                }
                return lambdaArguments;
            }

            @Override // org.jetbrains.kotlin.psi.Call
            public List<KtTypeProjection> getTypeArguments() {
                List<KtTypeProjection> typeArguments = ktCallElement.getTypeArguments();
                if (typeArguments == null) {
                    $$$reportNull$$$0(2);
                }
                return typeArguments;
            }

            @Override // org.jetbrains.kotlin.psi.Call
            public KtTypeArgumentList getTypeArgumentList() {
                return ktCallElement.getTypeArgumentList();
            }

            @Override // org.jetbrains.kotlin.psi.Call
            public KtElement getCallElement() {
                KtCallElement ktCallElement2 = ktCallElement;
                if (ktCallElement2 == null) {
                    $$$reportNull$$$0(3);
                }
                return ktCallElement2;
            }

            public String toString() {
                return DebugTextUtilKt.getDebugText(ktCallElement);
            }

            @Override // org.jetbrains.kotlin.psi.Call
            public Call.CallType getCallType() {
                Call.CallType callType = Call.CallType.DEFAULT;
                if (callType == null) {
                    $$$reportNull$$$0(4);
                }
                return callType;
            }
        };
    }

    public static Call makeCall(KtElement ktElement, ReceiverValue receiverValue) {
        if (ktElement == null) {
            $$$reportNull$$$0(30);
        }
        if (receiverValue == null) {
            $$$reportNull$$$0(31);
        }
        return new CallImpl(ktElement, receiverValue, null, null, Collections.emptyList());
    }
}
