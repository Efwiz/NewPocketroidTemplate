package java.lang.invoke;

/* JADX INFO: loaded from: NewCatroid_APKTemplate_2-2-0r.apk:assets/core-lambda-stubs.jar:java/lang/invoke/CallSite.class */
public abstract class CallSite {
    public MethodType type();

    public abstract MethodHandle getTarget();

    public abstract void setTarget(MethodHandle newTarget);

    public abstract MethodHandle dynamicInvoker();
}
