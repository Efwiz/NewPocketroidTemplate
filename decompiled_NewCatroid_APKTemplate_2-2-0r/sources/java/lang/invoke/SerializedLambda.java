package java.lang.invoke;

import java.io.Serializable;

/* JADX INFO: loaded from: NewCatroid_APKTemplate_2-2-0r.apk:assets/core-lambda-stubs.jar:java/lang/invoke/SerializedLambda.class */
public final class SerializedLambda implements Serializable {
    public SerializedLambda(Class<?> capturingClass, String functionalInterfaceClass, String functionalInterfaceMethodName, String functionalInterfaceMethodSignature, int implMethodKind, String implClass, String implMethodName, String implMethodSignature, String instantiatedMethodType, Object[] capturedArgs);

    public String getCapturingClass();

    public String getFunctionalInterfaceClass();

    public String getFunctionalInterfaceMethodName();

    public String getFunctionalInterfaceMethodSignature();

    public String getImplClass();

    public String getImplMethodName();

    public String getImplMethodSignature();

    public int getImplMethodKind();

    public final String getInstantiatedMethodType();

    public int getCapturedArgCount();

    public Object getCapturedArg(int i);
}
