package com.apksignaturekiller;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Map;

public class HookApplication extends Application implements InvocationHandler {
    private int GET_SIGNATURES = 64;
    private String appPkgName;
    private Object base;
    private Application realApplication;
    private byte[][] sign;

    @Nullable
    private Application ChangeApplication(String str, Context context) {
        try {
            Object invokeStaticMethod = invokeStaticMethod("android.app.ActivityThread", "currentActivityThread", new Class[0], new Object[0]);
            Object fieldValue = getFieldValue("android.app.ActivityThread", invokeStaticMethod, "mBoundApplication");
            Object fieldValue2 = getFieldValue("android.app.ActivityThread$AppBindData", fieldValue, "info");
            setFieldValue("android.app.LoadedApk", fieldValue2, "mApplication", null);
            ((ArrayList) getFieldValue("android.app.ActivityThread", invokeStaticMethod, "mAllApplications")).remove(getFieldValue("android.app.ActivityThread", invokeStaticMethod, "mInitialApplication"));
            ((ApplicationInfo) getFieldValue("android.app.LoadedApk", fieldValue2, "mApplicationInfo")).className = str;
            ((ApplicationInfo) getFieldValue("android.app.ActivityThread$AppBindData", fieldValue, "appInfo")).className = str;
            Object bool = new Boolean(false);
            Application application = (Application) invokeMethod("android.app.LoadedApk", fieldValue2, "makeApplication", new Object[]{bool, null}, Boolean.TYPE, Class.forName("android.app.Instrumentation"));
            setFieldOjbect("android.app.ActivityThread", "mInitialApplication", invokeStaticMethod, application);
            return application;
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    private void HookSign(Context context) {
        try {
            DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(Base64.decode("### Signatures Data ###", 0)));
            byte[][] bArr = new byte[(dataInputStream.read() & 255)][];
            for (int i = 0; i < bArr.length; i++) {
                bArr[i] = new byte[dataInputStream.readInt()];
                dataInputStream.readFully(bArr[i]);
            }
            @SuppressLint("PrivateApi") Class<?> cls = Class.forName("android.app.ActivityThread");
            Object invoke = cls.getDeclaredMethod("currentActivityThread", new Class[0]).invoke(null, new Object[0]);
            Field declaredField = cls.getDeclaredField("sPackageManager");
            declaredField.setAccessible(true);
            Object obj = declaredField.get(invoke);
            @SuppressLint("PrivateApi") Class<?> cls2 = Class.forName("android.content.pm.IPackageManager");
            this.base = obj;
            this.sign = bArr;
            this.appPkgName = context.getPackageName();
            Object newProxyInstance = Proxy.newProxyInstance(cls2.getClassLoader(), new Class[]{cls2}, this);
            declaredField.set(invoke, newProxyInstance);
            PackageManager packageManager = context.getPackageManager();
            Field declaredField2 = packageManager.getClass().getDeclaredField("mPM");
            declaredField2.setAccessible(true);
            declaredField2.set(packageManager, newProxyInstance);
            System.out.println("PmsHook success.");
        } catch (Exception e) {
            System.err.println("PmsHook failed.");
            e.printStackTrace();
        }
    }

    private void ReplaceApp(Context context) {
        try {
            File fileStreamPath = context.getFileStreamPath("hook.apk");
            if (!fileStreamPath.exists()) {
                InputStream open = context.getAssets().open("hook.apk");
                FileOutputStream fileOutputStream = new FileOutputStream(fileStreamPath);
                byte[] bArr = new byte[1024];
                for (int i = 0; i != -1; i = open.read(bArr)) {
                    fileOutputStream.write(bArr, 0, i);
                    fileOutputStream.flush();
                }
                open.close();
                fileOutputStream.close();
            }
            if (fileStreamPath != null && fileStreamPath.exists()) {
                String path = fileStreamPath.getPath();
                context.getClassLoader();
                @SuppressLint("PrivateApi") Field declaredField = ClassLoader.getSystemClassLoader().loadClass("android.app.ActivityThread").getDeclaredField("sCurrentActivityThread");
                declaredField.setAccessible(true);
                Object obj = declaredField.get(null);
                Field declaredField2 = obj.getClass().getDeclaredField("mPackages");
                declaredField2.setAccessible(true);
                Object obj2 = ((WeakReference) ((Map) declaredField2.get(obj)).get(context.getPackageName())).get();
                Field declaredField3 = obj2.getClass().getDeclaredField("mAppDir");
                declaredField3.setAccessible(true);
                declaredField3.set(obj2, path);
                Field declaredField4 = obj2.getClass().getDeclaredField("mApplicationInfo");
                declaredField4.setAccessible(true);
                ApplicationInfo applicationInfo = (ApplicationInfo) declaredField4.get(obj2);
                applicationInfo.publicSourceDir = path;
                applicationInfo.sourceDir = path;
            }
        } catch (Exception e) {
            System.err.println("PmsHook failed.");
            e.printStackTrace();
        }
    }

    private @Nullable Object currentActivityThread() {
        try {
            @SuppressLint("PrivateApi") Field declaredField = Class.forName("android.app.ActivityThread", false, Class.forName("com.apksignaturekiller.HookApplication").getClassLoader()).getDeclaredField("sCurrentActivityThread");
            declaredField.setAccessible(true);
            return declaredField.get(null);
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    private @Nullable Object getFieldValue(String str, Object obj, String str2) {
        try {
            Field declaredField = Class.forName(str).getDeclaredField(str2);
            declaredField.setAccessible(true);
            return declaredField.get(obj);
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    private @Nullable Object invokeMethod(String str, Object obj, String str2, Object[] objArr, Class<?>... clsArr) {
        try {
            Method declaredMethod = Class.forName(str).getDeclaredMethod(str2, clsArr);
            declaredMethod.setAccessible(true);
            return declaredMethod.invoke(obj, objArr);
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    private @Nullable Object invokeStaticMethod(String str, String str2, Class[] clsArr, Object[] objArr) {
        try {
            return Class.forName(str).getMethod(str2, clsArr).invoke(null, objArr);
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    private void setFieldOjbect(String str, String str2, Object obj, Object obj2) {
        try {
            Field declaredField = Class.forName(str).getDeclaredField(str2);
            declaredField.setAccessible(true);
            declaredField.set(obj, obj2);
        } catch (SecurityException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    private boolean setFieldValue(String str, Object obj, String str2, Object obj2) {
        try {
            Field declaredField = Class.forName(str).getDeclaredField(str2);
            declaredField.setAccessible(true);
            declaredField.set(obj, obj2);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void attachBaseContext(Context context) {
        HookSign(context);
        try {
            realApplication = (Application) Class.forName("### Applicaton Data ###").getConstructor(new Class[0]).newInstance(new Object[0]);
            ReplaceApp(context);
            super.attachBaseContext(context);
            if (realApplication != null) {
                issieiej(context, realApplication);
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    @Override
    public Object invoke(Object obj, @NotNull Method method, Object[] objArr) throws Throwable {
        if ("getPackageInfo".equals(method.getName())) {
            String str = (String) objArr[0];
            if (((Integer) objArr[1] & GET_SIGNATURES) != 0 && appPkgName.equals(str)) {
                PackageInfo packageInfo = (PackageInfo) method.invoke(base, objArr);
                packageInfo.signatures = new Signature[sign.length];
                for (int i = 0; i < packageInfo.signatures.length; i++) {
                    packageInfo.signatures[i] = new Signature(sign[i]);
                }
                return packageInfo;
            }
        }
        return method.invoke(base, objArr);
    }

    public void issieiej(Context context, Application application) {
        if (application != null) {
            try {
                Method declaredMethod = Class.forName("android.content.ContextWrapper").getDeclaredMethod("attachBaseContext", Class.forName("android.content.Context"));
                declaredMethod.setAccessible(true);
                declaredMethod.invoke(application, context);
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    public void onCreate() {
        super.onCreate();
        Application ChangeApplication = ChangeApplication("### Applicaton Data ###", this);
        if (ChangeApplication != null) {
            ChangeApplication.onCreate();
        }
    }
}
