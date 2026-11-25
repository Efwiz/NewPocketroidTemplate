package com.huawei.hms.ml.language.common.translate;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.huawei.hms.feature.dynamic.IObjectWrapper;

/* JADX INFO: loaded from: classes10.dex */
public interface IRemoteTranslateDelegate extends IInterface {
    public static final String DESCRIPTOR = "com.huawei.hms.ml.language.common.translate.IRemoteTranslateDelegate";

    public static class Default implements IRemoteTranslateDelegate {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.huawei.hms.ml.language.common.translate.IRemoteTranslateDelegate
        public int destroy() throws RemoteException {
            return 0;
        }

        @Override // com.huawei.hms.ml.language.common.translate.IRemoteTranslateDelegate
        public int getModelLevel(String str) throws RemoteException {
            return 0;
        }

        @Override // com.huawei.hms.ml.language.common.translate.IRemoteTranslateDelegate
        public int initial(IObjectWrapper iObjectWrapper, TranslateOptionsParcel translateOptionsParcel) throws RemoteException {
            return 0;
        }

        @Override // com.huawei.hms.ml.language.common.translate.IRemoteTranslateDelegate
        public TranslateParcel translate(Bundle bundle, TranslateFrameParcel translateFrameParcel, TranslateOptionsParcel translateOptionsParcel) throws RemoteException {
            return null;
        }
    }

    int destroy() throws RemoteException;

    int getModelLevel(String str) throws RemoteException;

    int initial(IObjectWrapper iObjectWrapper, TranslateOptionsParcel translateOptionsParcel) throws RemoteException;

    TranslateParcel translate(Bundle bundle, TranslateFrameParcel translateFrameParcel, TranslateOptionsParcel translateOptionsParcel) throws RemoteException;

    public static abstract class Stub extends Binder implements IRemoteTranslateDelegate {
        static final int TRANSACTION_destroy = 3;
        static final int TRANSACTION_getModelLevel = 4;
        static final int TRANSACTION_initial = 1;
        static final int TRANSACTION_translate = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IRemoteTranslateDelegate.DESCRIPTOR);
        }

        public static IRemoteTranslateDelegate asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IRemoteTranslateDelegate.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IRemoteTranslateDelegate)) {
                return (IRemoteTranslateDelegate) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1598968902) {
                parcel2.writeString(IRemoteTranslateDelegate.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                parcel.enforceInterface(IRemoteTranslateDelegate.DESCRIPTOR);
                int iInitial = initial(IObjectWrapper.Stub.asInterface(parcel.readStrongBinder()), parcel.readInt() != 0 ? TranslateOptionsParcel.CREATOR.createFromParcel(parcel) : null);
                parcel2.writeNoException();
                parcel2.writeInt(iInitial);
                return true;
            }
            if (i == 2) {
                parcel.enforceInterface(IRemoteTranslateDelegate.DESCRIPTOR);
                TranslateParcel translateParcelTranslate = translate(parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null, parcel.readInt() != 0 ? TranslateFrameParcel.CREATOR.createFromParcel(parcel) : null, parcel.readInt() != 0 ? TranslateOptionsParcel.CREATOR.createFromParcel(parcel) : null);
                parcel2.writeNoException();
                if (translateParcelTranslate != null) {
                    parcel2.writeInt(1);
                    translateParcelTranslate.writeToParcel(parcel2, 1);
                } else {
                    parcel2.writeInt(0);
                }
                return true;
            }
            if (i == 3) {
                parcel.enforceInterface(IRemoteTranslateDelegate.DESCRIPTOR);
                int iDestroy = destroy();
                parcel2.writeNoException();
                parcel2.writeInt(iDestroy);
                return true;
            }
            if (i == 4) {
                parcel.enforceInterface(IRemoteTranslateDelegate.DESCRIPTOR);
                int modelLevel = getModelLevel(parcel.readString());
                parcel2.writeNoException();
                parcel2.writeInt(modelLevel);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IRemoteTranslateDelegate {
            public static IRemoteTranslateDelegate sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRemoteTranslateDelegate.DESCRIPTOR;
            }

            @Override // com.huawei.hms.ml.language.common.translate.IRemoteTranslateDelegate
            public int initial(IObjectWrapper iObjectWrapper, TranslateOptionsParcel translateOptionsParcel) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteTranslateDelegate.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iObjectWrapper != null ? iObjectWrapper.asBinder() : null);
                    if (translateOptionsParcel != null) {
                        parcelObtain.writeInt(1);
                        translateOptionsParcel.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().initial(iObjectWrapper, translateOptionsParcel);
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.ml.language.common.translate.IRemoteTranslateDelegate
            public TranslateParcel translate(Bundle bundle, TranslateFrameParcel translateFrameParcel, TranslateOptionsParcel translateOptionsParcel) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteTranslateDelegate.DESCRIPTOR);
                    if (bundle != null) {
                        parcelObtain.writeInt(1);
                        bundle.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (translateFrameParcel != null) {
                        parcelObtain.writeInt(1);
                        translateFrameParcel.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (translateOptionsParcel != null) {
                        parcelObtain.writeInt(1);
                        translateOptionsParcel.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (!this.mRemote.transact(2, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().translate(bundle, translateFrameParcel, translateOptionsParcel);
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? TranslateParcel.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.ml.language.common.translate.IRemoteTranslateDelegate
            public int destroy() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteTranslateDelegate.DESCRIPTOR);
                    if (!this.mRemote.transact(3, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().destroy();
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.huawei.hms.ml.language.common.translate.IRemoteTranslateDelegate
            public int getModelLevel(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteTranslateDelegate.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    if (!this.mRemote.transact(4, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getModelLevel(str);
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IRemoteTranslateDelegate iRemoteTranslateDelegate) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (iRemoteTranslateDelegate == null) {
                return false;
            }
            Proxy.sDefaultImpl = iRemoteTranslateDelegate;
            return true;
        }

        public static IRemoteTranslateDelegate getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
