package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.utils.EnumC3891f;
import java.util.Set;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class V3 {
    static final /* synthetic */ boolean b = true;
    private final Set a;

    public V3(com.android.tools.r8.utils.A1 a1) {
        if (!b && !a1.B0().e(EnumC3891f.L)) {
            throw new AssertionError();
        }
        this.a = a(a1.a, a1.B0());
    }

    public final boolean a(C0767v2 c0767v2) {
        return !this.a.contains(c0767v2);
    }

    public static Set a(C0722p1 c0722p1, EnumC3891f enumC3891f) {
        Set setB = com.android.tools.r8.utils.T2.b(333);
        if (enumC3891f.a(EnumC3891f.B)) {
            U3.a(c0722p1, "Landroid/app/PendingIntent$CanceledException;", setB, "Landroid/content/ActivityNotFoundException;", "Landroid/content/IntentFilter$MalformedMimeTypeException;");
            U3.a(c0722p1, "Landroid/content/ReceiverCallNotAllowedException;", setB, "Landroid/content/pm/PackageManager$NameNotFoundException;", "Landroid/content/res/Resources$NotFoundException;");
            U3.a(c0722p1, "Landroid/database/CursorIndexOutOfBoundsException;", setB, "Landroid/database/SQLException;", "Landroid/database/StaleDataException;");
            U3.a(c0722p1, "Landroid/database/sqlite/SQLiteAbortException;", setB, "Landroid/database/sqlite/SQLiteConstraintException;", "Landroid/database/sqlite/SQLiteDatabaseCorruptException;");
            U3.a(c0722p1, "Landroid/database/sqlite/SQLiteDiskIOException;", setB, "Landroid/database/sqlite/SQLiteDoneException;", "Landroid/database/sqlite/SQLiteException;");
            U3.a(c0722p1, "Landroid/database/sqlite/SQLiteFullException;", setB, "Landroid/database/sqlite/SQLiteMisuseException;", "Landroid/net/ParseException;");
            U3.a(c0722p1, "Landroid/opengl/GLException;", setB, "Landroid/os/BadParcelableException;", "Landroid/os/DeadObjectException;");
            U3.a(c0722p1, "Landroid/os/ParcelFormatException;", setB, "Landroid/os/RemoteException;", "Landroid/provider/Settings$SettingNotFoundException;");
            U3.a(c0722p1, "Landroid/test/AssertionFailedError;", setB, "Landroid/test/ComparisonFailure;", "Landroid/util/AndroidException;");
            U3.a(c0722p1, "Landroid/util/AndroidRuntimeException;", setB, "Landroid/util/TimeFormatException;", "Landroid/view/InflateException;");
            U3.a(c0722p1, "Landroid/view/Surface$OutOfResourcesException;", setB, "Landroid/view/SurfaceHolder$BadSurfaceTypeException;", "Landroid/view/WindowManager$BadTokenException;");
            U3.a(c0722p1, "Landroid/widget/RemoteViews$ActionException;", setB, "Ljava/io/CharConversionException;", "Ljava/io/EOFException;");
            U3.a(c0722p1, "Ljava/io/FileNotFoundException;", setB, "Ljava/io/IOException;", "Ljava/io/InterruptedIOException;");
            U3.a(c0722p1, "Ljava/io/InvalidClassException;", setB, "Ljava/io/InvalidObjectException;", "Ljava/io/NotActiveException;");
            U3.a(c0722p1, "Ljava/io/NotSerializableException;", setB, "Ljava/io/ObjectStreamException;", "Ljava/io/OptionalDataException;");
            U3.a(c0722p1, "Ljava/io/StreamCorruptedException;", setB, "Ljava/io/SyncFailedException;", "Ljava/io/UTFDataFormatException;");
            U3.a(c0722p1, "Ljava/io/UnsupportedEncodingException;", setB, "Ljava/io/WriteAbortedException;", "Ljava/lang/AbstractMethodError;");
            U3.a(c0722p1, "Ljava/lang/ArithmeticException;", setB, "Ljava/lang/ArrayIndexOutOfBoundsException;", "Ljava/lang/ArrayStoreException;");
            U3.a(c0722p1, "Ljava/lang/AssertionError;", setB, "Ljava/lang/ClassCastException;", "Ljava/lang/ClassCircularityError;");
            U3.a(c0722p1, "Ljava/lang/ClassFormatError;", setB, "Ljava/lang/ClassNotFoundException;", "Ljava/lang/CloneNotSupportedException;");
            U3.a(c0722p1, "Ljava/lang/EnumConstantNotPresentException;", setB, "Ljava/lang/Error;", "Ljava/lang/Exception;");
            U3.a(c0722p1, "Ljava/lang/ExceptionInInitializerError;", setB, "Ljava/lang/IllegalAccessError;", "Ljava/lang/IllegalAccessException;");
            U3.a(c0722p1, "Ljava/lang/IllegalArgumentException;", setB, "Ljava/lang/IllegalMonitorStateException;", "Ljava/lang/IllegalStateException;");
            U3.a(c0722p1, "Ljava/lang/IllegalThreadStateException;", setB, "Ljava/lang/IncompatibleClassChangeError;", "Ljava/lang/IndexOutOfBoundsException;");
            U3.a(c0722p1, "Ljava/lang/InstantiationError;", setB, "Ljava/lang/InstantiationException;", "Ljava/lang/InternalError;");
            U3.a(c0722p1, "Ljava/lang/InterruptedException;", setB, "Ljava/lang/LinkageError;", "Ljava/lang/NegativeArraySizeException;");
            U3.a(c0722p1, "Ljava/lang/NoClassDefFoundError;", setB, "Ljava/lang/NoSuchFieldError;", "Ljava/lang/NoSuchFieldException;");
            U3.a(c0722p1, "Ljava/lang/NoSuchMethodError;", setB, "Ljava/lang/NoSuchMethodException;", "Ljava/lang/NullPointerException;");
            U3.a(c0722p1, "Ljava/lang/NumberFormatException;", setB, "Ljava/lang/OutOfMemoryError;", "Ljava/lang/RuntimeException;");
            U3.a(c0722p1, "Ljava/lang/SecurityException;", setB, "Ljava/lang/StackOverflowError;", "Ljava/lang/StringIndexOutOfBoundsException;");
            U3.a(c0722p1, "Ljava/lang/ThreadDeath;", setB, "Ljava/lang/Throwable;", "Ljava/lang/TypeNotPresentException;");
            U3.a(c0722p1, "Ljava/lang/UnknownError;", setB, "Ljava/lang/UnsatisfiedLinkError;", "Ljava/lang/UnsupportedClassVersionError;");
            U3.a(c0722p1, "Ljava/lang/UnsupportedOperationException;", setB, "Ljava/lang/VerifyError;", "Ljava/lang/VirtualMachineError;");
            U3.a(c0722p1, "Ljava/lang/annotation/AnnotationFormatError;", setB, "Ljava/lang/annotation/AnnotationTypeMismatchException;", "Ljava/lang/annotation/IncompleteAnnotationException;");
            U3.a(c0722p1, "Ljava/lang/reflect/GenericSignatureFormatError;", setB, "Ljava/lang/reflect/InvocationTargetException;", "Ljava/lang/reflect/MalformedParameterizedTypeException;");
            U3.a(c0722p1, "Ljava/lang/reflect/UndeclaredThrowableException;", setB, "Ljava/net/BindException;", "Ljava/net/ConnectException;");
            U3.a(c0722p1, "Ljava/net/HttpRetryException;", setB, "Ljava/net/MalformedURLException;", "Ljava/net/NoRouteToHostException;");
            U3.a(c0722p1, "Ljava/net/PortUnreachableException;", setB, "Ljava/net/ProtocolException;", "Ljava/net/SocketException;");
            U3.a(c0722p1, "Ljava/net/SocketTimeoutException;", setB, "Ljava/net/URISyntaxException;", "Ljava/net/UnknownHostException;");
            U3.a(c0722p1, "Ljava/net/UnknownServiceException;", setB, "Ljava/nio/BufferOverflowException;", "Ljava/nio/BufferUnderflowException;");
            U3.a(c0722p1, "Ljava/nio/InvalidMarkException;", setB, "Ljava/nio/ReadOnlyBufferException;", "Ljava/nio/channels/AlreadyConnectedException;");
            U3.a(c0722p1, "Ljava/nio/channels/AsynchronousCloseException;", setB, "Ljava/nio/channels/CancelledKeyException;", "Ljava/nio/channels/ClosedByInterruptException;");
            U3.a(c0722p1, "Ljava/nio/channels/ClosedChannelException;", setB, "Ljava/nio/channels/ClosedSelectorException;", "Ljava/nio/channels/ConnectionPendingException;");
            U3.a(c0722p1, "Ljava/nio/channels/FileLockInterruptionException;", setB, "Ljava/nio/channels/IllegalBlockingModeException;", "Ljava/nio/channels/IllegalSelectorException;");
            U3.a(c0722p1, "Ljava/nio/channels/NoConnectionPendingException;", setB, "Ljava/nio/channels/NonReadableChannelException;", "Ljava/nio/channels/NonWritableChannelException;");
            U3.a(c0722p1, "Ljava/nio/channels/NotYetBoundException;", setB, "Ljava/nio/channels/NotYetConnectedException;", "Ljava/nio/channels/OverlappingFileLockException;");
            U3.a(c0722p1, "Ljava/nio/channels/UnresolvedAddressException;", setB, "Ljava/nio/channels/UnsupportedAddressTypeException;", "Ljava/nio/charset/CharacterCodingException;");
            U3.a(c0722p1, "Ljava/nio/charset/CoderMalfunctionError;", setB, "Ljava/nio/charset/IllegalCharsetNameException;", "Ljava/nio/charset/MalformedInputException;");
            U3.a(c0722p1, "Ljava/nio/charset/UnmappableCharacterException;", setB, "Ljava/nio/charset/UnsupportedCharsetException;", "Ljava/security/AccessControlException;");
            U3.a(c0722p1, "Ljava/security/DigestException;", setB, "Ljava/security/GeneralSecurityException;", "Ljava/security/InvalidAlgorithmParameterException;");
            U3.a(c0722p1, "Ljava/security/InvalidKeyException;", setB, "Ljava/security/InvalidParameterException;", "Ljava/security/KeyException;");
            U3.a(c0722p1, "Ljava/security/KeyManagementException;", setB, "Ljava/security/KeyStoreException;", "Ljava/security/NoSuchAlgorithmException;");
            U3.a(c0722p1, "Ljava/security/NoSuchProviderException;", setB, "Ljava/security/PrivilegedActionException;", "Ljava/security/ProviderException;");
            U3.a(c0722p1, "Ljava/security/SignatureException;", setB, "Ljava/security/UnrecoverableEntryException;", "Ljava/security/UnrecoverableKeyException;");
            U3.a(c0722p1, "Ljava/security/acl/AclNotFoundException;", setB, "Ljava/security/acl/LastOwnerException;", "Ljava/security/acl/NotOwnerException;");
            U3.a(c0722p1, "Ljava/security/cert/CRLException;", setB, "Ljava/security/cert/CertPathBuilderException;", "Ljava/security/cert/CertPathValidatorException;");
            U3.a(c0722p1, "Ljava/security/cert/CertStoreException;", setB, "Ljava/security/cert/CertificateEncodingException;", "Ljava/security/cert/CertificateException;");
            U3.a(c0722p1, "Ljava/security/cert/CertificateExpiredException;", setB, "Ljava/security/cert/CertificateNotYetValidException;", "Ljava/security/cert/CertificateParsingException;");
            U3.a(c0722p1, "Ljava/security/spec/InvalidKeySpecException;", setB, "Ljava/security/spec/InvalidParameterSpecException;", "Ljava/sql/BatchUpdateException;");
            U3.a(c0722p1, "Ljava/sql/DataTruncation;", setB, "Ljava/sql/SQLException;", "Ljava/sql/SQLWarning;");
            U3.a(c0722p1, "Ljava/text/ParseException;", setB, "Ljava/util/ConcurrentModificationException;", "Ljava/util/DuplicateFormatFlagsException;");
            U3.a(c0722p1, "Ljava/util/EmptyStackException;", setB, "Ljava/util/FormatFlagsConversionMismatchException;", "Ljava/util/FormatterClosedException;");
            U3.a(c0722p1, "Ljava/util/IllegalFormatCodePointException;", setB, "Ljava/util/IllegalFormatConversionException;", "Ljava/util/IllegalFormatException;");
            U3.a(c0722p1, "Ljava/util/IllegalFormatFlagsException;", setB, "Ljava/util/IllegalFormatPrecisionException;", "Ljava/util/IllegalFormatWidthException;");
            U3.a(c0722p1, "Ljava/util/InputMismatchException;", setB, "Ljava/util/InvalidPropertiesFormatException;", "Ljava/util/MissingFormatArgumentException;");
            U3.a(c0722p1, "Ljava/util/MissingFormatWidthException;", setB, "Ljava/util/MissingResourceException;", "Ljava/util/NoSuchElementException;");
            U3.a(c0722p1, "Ljava/util/TooManyListenersException;", setB, "Ljava/util/UnknownFormatConversionException;", "Ljava/util/UnknownFormatFlagsException;");
            U3.a(c0722p1, "Ljava/util/concurrent/BrokenBarrierException;", setB, "Ljava/util/concurrent/CancellationException;", "Ljava/util/concurrent/ExecutionException;");
            U3.a(c0722p1, "Ljava/util/concurrent/RejectedExecutionException;", setB, "Ljava/util/concurrent/TimeoutException;", "Ljava/util/jar/JarException;");
            U3.a(c0722p1, "Ljava/util/prefs/BackingStoreException;", setB, "Ljava/util/prefs/InvalidPreferencesFormatException;", "Ljava/util/regex/PatternSyntaxException;");
            U3.a(c0722p1, "Ljava/util/zip/DataFormatException;", setB, "Ljava/util/zip/ZipException;", "Ljavax/crypto/BadPaddingException;");
            U3.a(c0722p1, "Ljavax/crypto/ExemptionMechanismException;", setB, "Ljavax/crypto/IllegalBlockSizeException;", "Ljavax/crypto/NoSuchPaddingException;");
            U3.a(c0722p1, "Ljavax/crypto/ShortBufferException;", setB, "Ljavax/net/ssl/SSLException;", "Ljavax/net/ssl/SSLHandshakeException;");
            U3.a(c0722p1, "Ljavax/net/ssl/SSLKeyException;", setB, "Ljavax/net/ssl/SSLPeerUnverifiedException;", "Ljavax/net/ssl/SSLProtocolException;");
            U3.a(c0722p1, "Ljavax/security/auth/DestroyFailedException;", setB, "Ljavax/security/auth/callback/UnsupportedCallbackException;", "Ljavax/security/auth/login/LoginException;");
            U3.a(c0722p1, "Ljavax/security/cert/CertificateEncodingException;", setB, "Ljavax/security/cert/CertificateException;", "Ljavax/security/cert/CertificateExpiredException;");
            U3.a(c0722p1, "Ljavax/security/cert/CertificateNotYetValidException;", setB, "Ljavax/security/cert/CertificateParsingException;", "Ljavax/xml/parsers/FactoryConfigurationError;");
            U3.a(c0722p1, "Ljavax/xml/parsers/ParserConfigurationException;", setB, "Ljunit/framework/AssertionFailedError;", "Ljunit/framework/ComparisonFailure;");
            U3.a(c0722p1, "Lorg/apache/http/ConnectionClosedException;", setB, "Lorg/apache/http/HttpException;", "Lorg/apache/http/MalformedChunkCodingException;");
            U3.a(c0722p1, "Lorg/apache/http/MethodNotSupportedException;", setB, "Lorg/apache/http/NoHttpResponseException;", "Lorg/apache/http/ParseException;");
            U3.a(c0722p1, "Lorg/apache/http/ProtocolException;", setB, "Lorg/apache/http/UnsupportedHttpVersionException;", "Lorg/apache/http/auth/AuthenticationException;");
            U3.a(c0722p1, "Lorg/apache/http/auth/InvalidCredentialsException;", setB, "Lorg/apache/http/auth/MalformedChallengeException;", "Lorg/apache/http/client/CircularRedirectException;");
            U3.a(c0722p1, "Lorg/apache/http/client/ClientProtocolException;", setB, "Lorg/apache/http/client/HttpResponseException;", "Lorg/apache/http/client/NonRepeatableRequestException;");
            U3.a(c0722p1, "Lorg/apache/http/client/RedirectException;", setB, "Lorg/apache/http/conn/ConnectTimeoutException;", "Lorg/apache/http/conn/ConnectionPoolTimeoutException;");
            U3.a(c0722p1, "Lorg/apache/http/conn/HttpHostConnectException;", setB, "Lorg/apache/http/cookie/MalformedCookieException;", "Lorg/apache/http/impl/auth/NTLMEngineException;");
            U3.a(c0722p1, "Lorg/apache/http/impl/auth/UnsupportedDigestAlgorithmException;", setB, "Lorg/apache/http/impl/client/TunnelRefusedException;", "Lorg/apache/http/impl/cookie/DateParseException;");
            U3.a(c0722p1, "Lorg/json/JSONException;", setB, "Lorg/w3c/dom/DOMException;", "Lorg/xml/sax/SAXException;");
            U3.a(c0722p1, "Lorg/xml/sax/SAXNotRecognizedException;", setB, "Lorg/xml/sax/SAXNotSupportedException;", "Lorg/xml/sax/SAXParseException;");
            setB.add(c0722p1.e("Lorg/xmlpull/v1/XmlPullParserException;"));
        }
        if (enumC3891f.a(EnumC3891f.D)) {
            setB.add(c0722p1.e("Landroid/content/IntentSender$SendIntentException;"));
        }
        if (enumC3891f.a(EnumC3891f.E)) {
            U3.a(c0722p1, "Landroid/accounts/AccountsException;", setB, "Landroid/accounts/AuthenticatorException;", "Landroid/accounts/NetworkErrorException;");
            setB.add(c0722p1.e("Landroid/accounts/OperationCanceledException;"));
            setB.add(c0722p1.e("Landroid/content/OperationApplicationException;"));
        }
        if (enumC3891f.a(EnumC3891f.F)) {
            U3.a(c0722p1, "Ljavax/xml/datatype/DatatypeConfigurationException;", setB, "Ljavax/xml/transform/TransformerConfigurationException;", "Ljavax/xml/transform/TransformerException;");
            U3.a(c0722p1, "Ljavax/xml/transform/TransformerFactoryConfigurationError;", setB, "Ljavax/xml/xpath/XPathException;", "Ljavax/xml/xpath/XPathExpressionException;");
            U3.a(c0722p1, "Ljavax/xml/xpath/XPathFactoryConfigurationException;", setB, "Ljavax/xml/xpath/XPathFunctionException;", "Lorg/w3c/dom/ls/LSException;");
        }
        if (enumC3891f.a(EnumC3891f.G)) {
            U3.a(c0722p1, "Landroid/net/sip/SipException;", setB, "Landroid/nfc/FormatException;", "Ljava/io/IOError;");
            U3.a(c0722p1, "Ljava/sql/SQLClientInfoException;", setB, "Ljava/sql/SQLDataException;", "Ljava/sql/SQLFeatureNotSupportedException;");
            U3.a(c0722p1, "Ljava/sql/SQLIntegrityConstraintViolationException;", setB, "Ljava/sql/SQLInvalidAuthorizationSpecException;", "Ljava/sql/SQLNonTransientConnectionException;");
            U3.a(c0722p1, "Ljava/sql/SQLNonTransientException;", setB, "Ljava/sql/SQLRecoverableException;", "Ljava/sql/SQLSyntaxErrorException;");
            U3.a(c0722p1, "Ljava/sql/SQLTimeoutException;", setB, "Ljava/sql/SQLTransactionRollbackException;", "Ljava/sql/SQLTransientConnectionException;");
            U3.a(c0722p1, "Ljava/sql/SQLTransientException;", setB, "Ljava/util/ServiceConfigurationError;", "Ljava/util/zip/ZipError;");
        }
        if (enumC3891f.a(EnumC3891f.G_MR1)) {
            setB.add(c0722p1.e("Landroid/nfc/TagLostException;"));
        }
        if (enumC3891f.a(EnumC3891f.H)) {
            U3.a(c0722p1, "Landroid/app/Fragment$InstantiationException;", setB, "Landroid/database/sqlite/SQLiteAccessPermException;", "Landroid/database/sqlite/SQLiteBindOrColumnIndexOutOfRangeException;");
            U3.a(c0722p1, "Landroid/database/sqlite/SQLiteBlobTooBigException;", setB, "Landroid/database/sqlite/SQLiteCantOpenDatabaseException;", "Landroid/database/sqlite/SQLiteDatabaseLockedException;");
            U3.a(c0722p1, "Landroid/database/sqlite/SQLiteDatatypeMismatchException;", setB, "Landroid/database/sqlite/SQLiteOutOfMemoryException;", "Landroid/database/sqlite/SQLiteReadOnlyDatabaseException;");
            U3.a(c0722p1, "Landroid/database/sqlite/SQLiteTableLockedException;", setB, "Landroid/graphics/SurfaceTexture$OutOfResourcesException;", "Landroid/os/NetworkOnMainThreadException;");
            U3.a(c0722p1, "Landroid/renderscript/RSDriverException;", setB, "Landroid/renderscript/RSIllegalArgumentException;", "Landroid/renderscript/RSInvalidStateException;");
            U3.a(c0722p1, "Landroid/renderscript/RSRuntimeException;", setB, "Landroid/util/Base64DataException;", "Landroid/util/MalformedJsonException;");
            setB.add(c0722p1.e("Landroid/view/KeyCharacterMap$UnavailableException;"));
        }
        if (enumC3891f.a(EnumC3891f.I)) {
            setB.add(c0722p1.e("Landroid/security/KeyChainException;"));
            setB.add(c0722p1.e("Landroid/util/NoSuchPropertyException;"));
        }
        if (enumC3891f.a(EnumC3891f.I_MR1)) {
            setB.add(c0722p1.e("Landroid/os/TransactionTooLargeException;"));
        }
        if (enumC3891f.a(EnumC3891f.J)) {
            U3.a(c0722p1, "Landroid/media/MediaCodec$CryptoException;", setB, "Landroid/media/MediaCryptoException;", "Landroid/os/OperationCanceledException;");
        }
        if (enumC3891f.a(EnumC3891f.J_MR1)) {
            setB.add(c0722p1.e("Landroid/view/WindowManager$InvalidDisplayException;"));
        }
        if (enumC3891f.a(EnumC3891f.J_MR2)) {
            U3.a(c0722p1, "Landroid/media/DeniedByServerException;", setB, "Landroid/media/MediaDrmException;", "Landroid/media/NotProvisionedException;");
            setB.add(c0722p1.e("Landroid/media/UnsupportedSchemeException;"));
        }
        if (enumC3891f.a(EnumC3891f.K)) {
            U3.a(c0722p1, "Landroid/media/ResourceBusyException;", setB, "Landroid/os/ParcelFileDescriptor$FileDescriptorDetachedException;", "Ljava/lang/ReflectiveOperationException;");
            setB.add(c0722p1.e("Ljavax/crypto/AEADBadTagException;"));
        }
        return setB;
    }
}
