package org.jetbrains.kotlin.com.intellij.util.messages;

import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;

/* JADX INFO: loaded from: classes3.dex */
public class Topic<L> {
    private final BroadcastDirection myBroadcastDirection;
    private final String myDisplayName;
    private final boolean myImmediateDelivery;
    private final Class<L> myListenerClass;

    public enum BroadcastDirection {
        TO_CHILDREN,
        TO_DIRECT_CHILDREN,
        NONE,
        TO_PARENT
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 10 || i == 11 || i == 16) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 10 || i == 11 || i == 16) ? 2 : 3];
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 5:
            case 8:
            case 13:
            case 15:
                objArr[0] = "listenerClass";
                break;
            case 4:
            case 6:
            case 9:
                objArr[0] = "broadcastDirection";
                break;
            case 7:
            default:
                objArr[0] = "name";
                break;
            case 10:
            case 11:
            case 16:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/messages/Topic";
                break;
            case 12:
            case 14:
                objArr[0] = "displayName";
                break;
        }
        if (i == 10) {
            objArr[1] = "getDisplayName";
        } else if (i == 11) {
            objArr[1] = "getListenerClass";
        } else if (i != 16) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/messages/Topic";
        } else {
            objArr[1] = "getBroadcastDirection";
        }
        switch (i) {
            case 10:
            case 11:
            case 16:
                break;
            case 12:
            case 13:
            case 14:
            case 15:
                objArr[2] = CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME;
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 10 && i != 11 && i != 16) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public Topic(String str, Class<L> cls) {
        this(str, cls, BroadcastDirection.TO_CHILDREN);
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        if (cls == null) {
            $$$reportNull$$$0(1);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public Topic(Class<L> cls, BroadcastDirection broadcastDirection) {
        this(cls.getSimpleName(), cls, broadcastDirection);
        if (cls == null) {
            $$$reportNull$$$0(3);
        }
        if (broadcastDirection == null) {
            $$$reportNull$$$0(4);
        }
    }

    public Topic(Class<L> cls, BroadcastDirection broadcastDirection, boolean z) {
        if (cls == null) {
            $$$reportNull$$$0(5);
        }
        if (broadcastDirection == null) {
            $$$reportNull$$$0(6);
        }
        this.myDisplayName = cls.getSimpleName();
        this.myListenerClass = cls;
        this.myBroadcastDirection = broadcastDirection;
        this.myImmediateDelivery = z;
    }

    public Topic(String str, Class<L> cls, BroadcastDirection broadcastDirection) {
        if (str == null) {
            $$$reportNull$$$0(7);
        }
        if (cls == null) {
            $$$reportNull$$$0(8);
        }
        if (broadcastDirection == null) {
            $$$reportNull$$$0(9);
        }
        this.myDisplayName = str;
        this.myListenerClass = cls;
        this.myBroadcastDirection = broadcastDirection;
        this.myImmediateDelivery = false;
    }

    public Class<L> getListenerClass() {
        Class<L> cls = this.myListenerClass;
        if (cls == null) {
            $$$reportNull$$$0(11);
        }
        return cls;
    }

    public String toString() {
        return "Topic(name='" + this.myDisplayName + "', listenerClass=" + this.myListenerClass + ", broadcastDirection=" + this.myBroadcastDirection + ", immediateDelivery=" + this.myImmediateDelivery + ')';
    }

    public static <L> Topic<L> create(String str, Class<L> cls) {
        if (str == null) {
            $$$reportNull$$$0(12);
        }
        if (cls == null) {
            $$$reportNull$$$0(13);
        }
        return new Topic<>(str, cls);
    }

    public BroadcastDirection getBroadcastDirection() {
        BroadcastDirection broadcastDirection = this.myBroadcastDirection;
        if (broadcastDirection == null) {
            $$$reportNull$$$0(16);
        }
        return broadcastDirection;
    }

    public boolean isImmediateDelivery() {
        return this.myImmediateDelivery;
    }
}
