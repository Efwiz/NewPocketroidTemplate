package java.lang.invoke;

/* JADX INFO: loaded from: NewCatroid_APKTemplate_2-2-0r.apk:assets/core-lambda-stubs.jar:java/lang/invoke/LambdaMetafactory.class */
public class LambdaMetafactory {
    public static final int FLAG_SERIALIZABLE = 1;
    public static final int FLAG_MARKERS = 2;
    public static final int FLAG_BRIDGES = 4;

    public static CallSite metafactory(MethodHandles.Lookup caller, String invokedName, MethodType invokedType, MethodType samMethodType, MethodHandle implMethod, MethodType instantiatedMethodType) throws LambdaConversionException;

    public static CallSite altMetafactory(MethodHandles.Lookup caller, String invokedName, MethodType invokedType, Object... args) throws LambdaConversionException;
}
