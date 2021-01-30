# classes2.dex

.class public Lcom/apksignaturekiller/HookApplication;
.super Landroid/app/Application;
.source "HookApplication.java"

# interfaces
.implements Ljava/lang/reflect/InvocationHandler;


# instance fields
.field private GET_SIGNATURES:I

.field private appPkgName:Ljava/lang/String;

.field private base:Ljava/lang/Object;

.field private realApplication:Landroid/app/Application;

.field private sign:[[B


# direct methods
.method public constructor <init>()V
    .registers 2

    .line 28
    invoke-direct {p0}, Landroid/app/Application;-><init>()V

    .line 29
    const/16 v0, 0x40

    iput v0, p0, Lcom/apksignaturekiller/HookApplication;->GET_SIGNATURES:I

    return-void
.end method

.method private ChangeApplication(Ljava/lang/String;Landroid/content/Context;)Landroid/app/Application;
    .registers 19
    .param p1, "str"  # Ljava/lang/String;
    .param p2, "context"  # Landroid/content/Context;

    .line 38
    move-object/from16 v7, p0

    move-object/from16 v8, p1

    const-string v0, "mInitialApplication"

    const-string v1, "android.app.LoadedApk"

    const-string v2, "android.app.ActivityThread$AppBindData"

    const-string v9, "android.app.ActivityThread"

    const/4 v10, 0x0

    :try_start_d
    const-string v3, "currentActivityThread"

    const/4 v4, 0x0

    new-array v5, v4, [Ljava/lang/Class;

    new-array v6, v4, [Ljava/lang/Object;

    invoke-direct {v7, v9, v3, v5, v6}, Lcom/apksignaturekiller/HookApplication;->invokeStaticMethod(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Class;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    move-object v11, v3

    .line 39
    .local v11, "invokeStaticMethod":Ljava/lang/Object;
    const-string v3, "mBoundApplication"

    invoke-direct {v7, v9, v11, v3}, Lcom/apksignaturekiller/HookApplication;->getFieldValue(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v3

    move-object v12, v3

    .line 40
    .local v12, "fieldValue":Ljava/lang/Object;
    const-string v3, "info"

    invoke-direct {v7, v2, v12, v3}, Lcom/apksignaturekiller/HookApplication;->getFieldValue(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v3

    move-object v13, v3

    .line 41
    .local v13, "fieldValue2":Ljava/lang/Object;
    const-string v3, "mApplication"

    invoke-direct {v7, v1, v13, v3, v10}, Lcom/apksignaturekiller/HookApplication;->setFieldValue(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;)Z

    .line 42
    const-string v3, "mAllApplications"

    invoke-direct {v7, v9, v11, v3}, Lcom/apksignaturekiller/HookApplication;->getFieldValue(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Ljava/util/ArrayList;

    invoke-direct {v7, v9, v11, v0}, Lcom/apksignaturekiller/HookApplication;->getFieldValue(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v5

    invoke-virtual {v3, v5}, Ljava/util/ArrayList;->remove(Ljava/lang/Object;)Z

    .line 43
    const-string v3, "mApplicationInfo"

    invoke-direct {v7, v1, v13, v3}, Lcom/apksignaturekiller/HookApplication;->getFieldValue(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Landroid/content/pm/ApplicationInfo;

    iput-object v8, v1, Landroid/content/pm/ApplicationInfo;->className:Ljava/lang/String;

    .line 44
    const-string v1, "appInfo"

    invoke-direct {v7, v2, v12, v1}, Lcom/apksignaturekiller/HookApplication;->getFieldValue(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Landroid/content/pm/ApplicationInfo;

    iput-object v8, v1, Landroid/content/pm/ApplicationInfo;->className:Ljava/lang/String;

    .line 45
    new-instance v1, Ljava/lang/Boolean;

    invoke-direct {v1, v4}, Ljava/lang/Boolean;-><init>(Z)V

    move-object v14, v1

    .line 46
    .local v14, "bool":Ljava/lang/Object;
    const-string v2, "android.app.LoadedApk"

    const-string v5, "makeApplication"

    const/4 v1, 0x2

    new-array v6, v1, [Ljava/lang/Object;

    aput-object v14, v6, v4

    const/4 v3, 0x1

    aput-object v10, v6, v3

    new-array v15, v1, [Ljava/lang/Class;

    sget-object v1, Ljava/lang/Boolean;->TYPE:Ljava/lang/Class;

    aput-object v1, v15, v4

    const-string v1, "android.app.Instrumentation"

    invoke-static {v1}, Ljava/lang/Class;->forName(Ljava/lang/String;)Ljava/lang/Class;

    move-result-object v1

    aput-object v1, v15, v3

    move-object/from16 v1, p0

    move-object v3, v13

    move-object v4, v5

    move-object v5, v6

    move-object v6, v15

    invoke-direct/range {v1 .. v6}, Lcom/apksignaturekiller/HookApplication;->invokeMethod(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/String;[Ljava/lang/Object;[Ljava/lang/Class;)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Landroid/app/Application;

    .line 47
    .local v1, "application":Landroid/app/Application;
    invoke-direct {v7, v9, v0, v11, v1}, Lcom/apksignaturekiller/HookApplication;->setFieldOjbect(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V
    :try_end_7e
    .catchall {:try_start_d .. :try_end_7e} :catchall_7f

    .line 48
    return-object v1

    .line 49
    .end local v1  # "application":Landroid/app/Application;
    .end local v11  # "invokeStaticMethod":Ljava/lang/Object;
    .end local v12  # "fieldValue":Ljava/lang/Object;
    .end local v13  # "fieldValue2":Ljava/lang/Object;
    .end local v14  # "bool":Ljava/lang/Object;
    :catchall_7f
    move-exception v0

    .line 50
    .local v0, "th":Ljava/lang/Throwable;
    invoke-virtual {v0}, Ljava/lang/Throwable;->printStackTrace()V

    .line 51
    return-object v10
.end method

.method private HookSign(Landroid/content/Context;)V
    .registers 14
    .param p1, "context"  # Landroid/content/Context;

    .line 57
    :try_start_0
    new-instance v0, Ljava/io/DataInputStream;

    new-instance v1, Ljava/io/ByteArrayInputStream;

    const-string v2, "### Signatures Data ###"

    const/4 v3, 0x0

    invoke-static {v2, v3}, Landroid/util/Base64;->decode(Ljava/lang/String;I)[B

    move-result-object v2

    invoke-direct {v1, v2}, Ljava/io/ByteArrayInputStream;-><init>([B)V

    invoke-direct {v0, v1}, Ljava/io/DataInputStream;-><init>(Ljava/io/InputStream;)V

    .line 58
    .local v0, "dataInputStream":Ljava/io/DataInputStream;
    invoke-virtual {v0}, Ljava/io/DataInputStream;->read()I

    move-result v1

    and-int/lit16 v1, v1, 0xff

    new-array v1, v1, [[B

    .line 59
    .local v1, "bArr":[[B
    const/4 v2, 0x0

    .local v2, "i":I
    :goto_1a
    array-length v4, v1

    if-ge v2, v4, :cond_2d

    .line 60
    invoke-virtual {v0}, Ljava/io/DataInputStream;->readInt()I

    move-result v4

    new-array v4, v4, [B

    aput-object v4, v1, v2

    .line 61
    aget-object v4, v1, v2

    invoke-virtual {v0, v4}, Ljava/io/DataInputStream;->readFully([B)V

    .line 59
    add-int/lit8 v2, v2, 0x1

    goto :goto_1a

    .line 63
    .end local v2  # "i":I
    :cond_2d
    const-string v2, "android.app.ActivityThread"

    invoke-static {v2}, Ljava/lang/Class;->forName(Ljava/lang/String;)Ljava/lang/Class;

    move-result-object v2

    .line 64
    .local v2, "cls":Ljava/lang/Class;, "Ljava/lang/Class<*>;"
    const-string v4, "currentActivityThread"

    new-array v5, v3, [Ljava/lang/Class;

    invoke-virtual {v2, v4, v5}, Ljava/lang/Class;->getDeclaredMethod(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;

    move-result-object v4

    const/4 v5, 0x0

    new-array v6, v3, [Ljava/lang/Object;

    invoke-virtual {v4, v5, v6}, Ljava/lang/reflect/Method;->invoke(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v4

    .line 65
    .local v4, "invoke":Ljava/lang/Object;
    const-string v5, "sPackageManager"

    invoke-virtual {v2, v5}, Ljava/lang/Class;->getDeclaredField(Ljava/lang/String;)Ljava/lang/reflect/Field;

    move-result-object v5

    .line 66
    .local v5, "declaredField":Ljava/lang/reflect/Field;
    const/4 v6, 0x1

    invoke-virtual {v5, v6}, Ljava/lang/reflect/Field;->setAccessible(Z)V

    .line 67
    invoke-virtual {v5, v4}, Ljava/lang/reflect/Field;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v7

    .line 68
    .local v7, "obj":Ljava/lang/Object;
    const-string v8, "android.content.pm.IPackageManager"

    invoke-static {v8}, Ljava/lang/Class;->forName(Ljava/lang/String;)Ljava/lang/Class;

    move-result-object v8

    .line 69
    .local v8, "cls2":Ljava/lang/Class;, "Ljava/lang/Class<*>;"
    iput-object v7, p0, Lcom/apksignaturekiller/HookApplication;->base:Ljava/lang/Object;

    .line 70
    iput-object v1, p0, Lcom/apksignaturekiller/HookApplication;->sign:[[B

    .line 71
    invoke-virtual {p1}, Landroid/content/Context;->getPackageName()Ljava/lang/String;

    move-result-object v9

    iput-object v9, p0, Lcom/apksignaturekiller/HookApplication;->appPkgName:Ljava/lang/String;

    .line 72
    invoke-virtual {v8}, Ljava/lang/Class;->getClassLoader()Ljava/lang/ClassLoader;

    move-result-object v9

    new-array v10, v6, [Ljava/lang/Class;

    aput-object v8, v10, v3

    invoke-static {v9, v10, p0}, Ljava/lang/reflect/Proxy;->newProxyInstance(Ljava/lang/ClassLoader;[Ljava/lang/Class;Ljava/lang/reflect/InvocationHandler;)Ljava/lang/Object;

    move-result-object v3

    .line 73
    .local v3, "newProxyInstance":Ljava/lang/Object;
    invoke-virtual {v5, v4, v3}, Ljava/lang/reflect/Field;->set(Ljava/lang/Object;Ljava/lang/Object;)V

    .line 74
    invoke-virtual {p1}, Landroid/content/Context;->getPackageManager()Landroid/content/pm/PackageManager;

    move-result-object v9

    .line 75
    .local v9, "packageManager":Landroid/content/pm/PackageManager;
    invoke-virtual {v9}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    move-result-object v10

    const-string v11, "mPM"

    invoke-virtual {v10, v11}, Ljava/lang/Class;->getDeclaredField(Ljava/lang/String;)Ljava/lang/reflect/Field;

    move-result-object v10

    .line 76
    .local v10, "declaredField2":Ljava/lang/reflect/Field;
    invoke-virtual {v10, v6}, Ljava/lang/reflect/Field;->setAccessible(Z)V

    .line 77
    invoke-virtual {v10, v9, v3}, Ljava/lang/reflect/Field;->set(Ljava/lang/Object;Ljava/lang/Object;)V

    .line 78
    sget-object v6, Ljava/lang/System;->out:Ljava/io/PrintStream;

    const-string v11, "PmsHook success."

    invoke-virtual {v6, v11}, Ljava/io/PrintStream;->println(Ljava/lang/String;)V
    :try_end_8a
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_8a} :catch_8b

    .line 82
    .end local v0  # "dataInputStream":Ljava/io/DataInputStream;
    .end local v1  # "bArr":[[B
    .end local v2  # "cls":Ljava/lang/Class;, "Ljava/lang/Class<*>;"
    .end local v3  # "newProxyInstance":Ljava/lang/Object;
    .end local v4  # "invoke":Ljava/lang/Object;
    .end local v5  # "declaredField":Ljava/lang/reflect/Field;
    .end local v7  # "obj":Ljava/lang/Object;
    .end local v8  # "cls2":Ljava/lang/Class;, "Ljava/lang/Class<*>;"
    .end local v9  # "packageManager":Landroid/content/pm/PackageManager;
    .end local v10  # "declaredField2":Ljava/lang/reflect/Field;
    goto :goto_96

    .line 79
    :catch_8b
    move-exception v0

    .line 80
    .local v0, "e":Ljava/lang/Exception;
    sget-object v1, Ljava/lang/System;->err:Ljava/io/PrintStream;

    const-string v2, "PmsHook failed."

    invoke-virtual {v1, v2}, Ljava/io/PrintStream;->println(Ljava/lang/String;)V

    .line 81
    invoke-virtual {v0}, Ljava/lang/Exception;->printStackTrace()V

    .line 83
    .end local v0  # "e":Ljava/lang/Exception;
    :goto_96
    return-void
.end method

.method private ReplaceApp(Landroid/content/Context;)V
    .registers 12
    .param p1, "context"  # Landroid/content/Context;

    .line 87
    const-string v0, "hook.apk"

    :try_start_2
    invoke-virtual {p1, v0}, Landroid/content/Context;->getFileStreamPath(Ljava/lang/String;)Ljava/io/File;

    move-result-object v1

    .line 88
    .local v1, "fileStreamPath":Ljava/io/File;
    invoke-virtual {v1}, Ljava/io/File;->exists()Z

    move-result v2

    if-nez v2, :cond_34

    .line 89
    invoke-virtual {p1}, Landroid/content/Context;->getAssets()Landroid/content/res/AssetManager;

    move-result-object v2

    invoke-virtual {v2, v0}, Landroid/content/res/AssetManager;->open(Ljava/lang/String;)Ljava/io/InputStream;

    move-result-object v0

    .line 90
    .local v0, "open":Ljava/io/InputStream;
    new-instance v2, Ljava/io/FileOutputStream;

    invoke-direct {v2, v1}, Ljava/io/FileOutputStream;-><init>(Ljava/io/File;)V

    .line 91
    .local v2, "fileOutputStream":Ljava/io/FileOutputStream;
    const/16 v3, 0x400

    new-array v3, v3, [B

    .line 92
    .local v3, "bArr":[B
    const/4 v4, 0x0

    .local v4, "i":I
    :goto_1e
    const/4 v5, -0x1

    if-eq v4, v5, :cond_2e

    .line 93
    const/4 v5, 0x0

    invoke-virtual {v2, v3, v5, v4}, Ljava/io/FileOutputStream;->write([BII)V

    .line 94
    invoke-virtual {v2}, Ljava/io/FileOutputStream;->flush()V

    .line 92
    invoke-virtual {v0, v3}, Ljava/io/InputStream;->read([B)I

    move-result v5

    move v4, v5

    goto :goto_1e

    .line 96
    .end local v4  # "i":I
    :cond_2e
    invoke-virtual {v0}, Ljava/io/InputStream;->close()V

    .line 97
    invoke-virtual {v2}, Ljava/io/FileOutputStream;->close()V

    .line 99
    .end local v0  # "open":Ljava/io/InputStream;
    .end local v2  # "fileOutputStream":Ljava/io/FileOutputStream;
    .end local v3  # "bArr":[B
    :cond_34
    if-eqz v1, :cond_a4

    invoke-virtual {v1}, Ljava/io/File;->exists()Z

    move-result v0

    if-eqz v0, :cond_a4

    .line 100
    invoke-virtual {v1}, Ljava/io/File;->getPath()Ljava/lang/String;

    move-result-object v0

    .line 101
    .local v0, "path":Ljava/lang/String;
    invoke-virtual {p1}, Landroid/content/Context;->getClassLoader()Ljava/lang/ClassLoader;

    .line 102
    invoke-static {}, Ljava/lang/ClassLoader;->getSystemClassLoader()Ljava/lang/ClassLoader;

    move-result-object v2

    const-string v3, "android.app.ActivityThread"

    invoke-virtual {v2, v3}, Ljava/lang/ClassLoader;->loadClass(Ljava/lang/String;)Ljava/lang/Class;

    move-result-object v2

    const-string v3, "sCurrentActivityThread"

    invoke-virtual {v2, v3}, Ljava/lang/Class;->getDeclaredField(Ljava/lang/String;)Ljava/lang/reflect/Field;

    move-result-object v2

    .line 103
    .local v2, "declaredField":Ljava/lang/reflect/Field;
    const/4 v3, 0x1

    invoke-virtual {v2, v3}, Ljava/lang/reflect/Field;->setAccessible(Z)V

    .line 104
    const/4 v4, 0x0

    invoke-virtual {v2, v4}, Ljava/lang/reflect/Field;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v4

    .line 105
    .local v4, "obj":Ljava/lang/Object;
    invoke-virtual {v4}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    move-result-object v5

    const-string v6, "mPackages"

    invoke-virtual {v5, v6}, Ljava/lang/Class;->getDeclaredField(Ljava/lang/String;)Ljava/lang/reflect/Field;

    move-result-object v5

    .line 106
    .local v5, "declaredField2":Ljava/lang/reflect/Field;
    invoke-virtual {v5, v3}, Ljava/lang/reflect/Field;->setAccessible(Z)V

    .line 107
    invoke-virtual {v5, v4}, Ljava/lang/reflect/Field;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v6

    check-cast v6, Ljava/util/Map;

    invoke-virtual {p1}, Landroid/content/Context;->getPackageName()Ljava/lang/String;

    move-result-object v7

    invoke-interface {v6, v7}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v6

    check-cast v6, Ljava/lang/ref/WeakReference;

    invoke-virtual {v6}, Ljava/lang/ref/WeakReference;->get()Ljava/lang/Object;

    move-result-object v6

    .line 108
    .local v6, "obj2":Ljava/lang/Object;
    invoke-virtual {v6}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    move-result-object v7

    const-string v8, "mAppDir"

    invoke-virtual {v7, v8}, Ljava/lang/Class;->getDeclaredField(Ljava/lang/String;)Ljava/lang/reflect/Field;

    move-result-object v7

    .line 109
    .local v7, "declaredField3":Ljava/lang/reflect/Field;
    invoke-virtual {v7, v3}, Ljava/lang/reflect/Field;->setAccessible(Z)V

    .line 110
    invoke-virtual {v7, v6, v0}, Ljava/lang/reflect/Field;->set(Ljava/lang/Object;Ljava/lang/Object;)V

    .line 111
    invoke-virtual {v6}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    move-result-object v8

    const-string v9, "mApplicationInfo"

    invoke-virtual {v8, v9}, Ljava/lang/Class;->getDeclaredField(Ljava/lang/String;)Ljava/lang/reflect/Field;

    move-result-object v8

    .line 112
    .local v8, "declaredField4":Ljava/lang/reflect/Field;
    invoke-virtual {v8, v3}, Ljava/lang/reflect/Field;->setAccessible(Z)V

    .line 113
    invoke-virtual {v8, v6}, Ljava/lang/reflect/Field;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Landroid/content/pm/ApplicationInfo;

    .line 114
    .local v3, "applicationInfo":Landroid/content/pm/ApplicationInfo;
    iput-object v0, v3, Landroid/content/pm/ApplicationInfo;->publicSourceDir:Ljava/lang/String;

    .line 115
    iput-object v0, v3, Landroid/content/pm/ApplicationInfo;->sourceDir:Ljava/lang/String;
    :try_end_a4
    .catch Ljava/lang/Exception; {:try_start_2 .. :try_end_a4} :catch_a5

    .line 120
    .end local v0  # "path":Ljava/lang/String;
    .end local v1  # "fileStreamPath":Ljava/io/File;
    .end local v2  # "declaredField":Ljava/lang/reflect/Field;
    .end local v3  # "applicationInfo":Landroid/content/pm/ApplicationInfo;
    .end local v4  # "obj":Ljava/lang/Object;
    .end local v5  # "declaredField2":Ljava/lang/reflect/Field;
    .end local v6  # "obj2":Ljava/lang/Object;
    .end local v7  # "declaredField3":Ljava/lang/reflect/Field;
    .end local v8  # "declaredField4":Ljava/lang/reflect/Field;
    :cond_a4
    goto :goto_b0

    .line 117
    :catch_a5
    move-exception v0

    .line 118
    .local v0, "e":Ljava/lang/Exception;
    sget-object v1, Ljava/lang/System;->err:Ljava/io/PrintStream;

    const-string v2, "PmsHook failed."

    invoke-virtual {v1, v2}, Ljava/io/PrintStream;->println(Ljava/lang/String;)V

    .line 119
    invoke-virtual {v0}, Ljava/lang/Exception;->printStackTrace()V

    .line 121
    .end local v0  # "e":Ljava/lang/Exception;
    :goto_b0
    return-void
.end method

.method private currentActivityThread()Ljava/lang/Object;
    .registers 5

    .line 125
    const/4 v0, 0x0

    :try_start_1
    const-string v1, "android.app.ActivityThread"

    const/4 v2, 0x0

    const-string v3, "com.apksignaturekiller.HookApplication"

    invoke-static {v3}, Ljava/lang/Class;->forName(Ljava/lang/String;)Ljava/lang/Class;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/Class;->getClassLoader()Ljava/lang/ClassLoader;

    move-result-object v3

    invoke-static {v1, v2, v3}, Ljava/lang/Class;->forName(Ljava/lang/String;ZLjava/lang/ClassLoader;)Ljava/lang/Class;

    move-result-object v1

    const-string v2, "sCurrentActivityThread"

    invoke-virtual {v1, v2}, Ljava/lang/Class;->getDeclaredField(Ljava/lang/String;)Ljava/lang/reflect/Field;

    move-result-object v1

    .line 126
    .local v1, "declaredField":Ljava/lang/reflect/Field;
    const/4 v2, 0x1

    invoke-virtual {v1, v2}, Ljava/lang/reflect/Field;->setAccessible(Z)V

    .line 127
    invoke-virtual {v1, v0}, Ljava/lang/reflect/Field;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0
    :try_end_20
    .catchall {:try_start_1 .. :try_end_20} :catchall_21

    return-object v0

    .line 128
    .end local v1  # "declaredField":Ljava/lang/reflect/Field;
    :catchall_21
    move-exception v1

    .line 129
    .local v1, "th":Ljava/lang/Throwable;
    invoke-virtual {v1}, Ljava/lang/Throwable;->printStackTrace()V

    .line 130
    return-object v0
.end method

.method private getFieldValue(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
    .registers 6
    .param p1, "str"  # Ljava/lang/String;
    .param p2, "obj"  # Ljava/lang/Object;
    .param p3, "str2"  # Ljava/lang/String;

    .line 136
    :try_start_0
    invoke-static {p1}, Ljava/lang/Class;->forName(Ljava/lang/String;)Ljava/lang/Class;

    move-result-object v0

    invoke-virtual {v0, p3}, Ljava/lang/Class;->getDeclaredField(Ljava/lang/String;)Ljava/lang/reflect/Field;

    move-result-object v0

    .line 137
    .local v0, "declaredField":Ljava/lang/reflect/Field;
    const/4 v1, 0x1

    invoke-virtual {v0, v1}, Ljava/lang/reflect/Field;->setAccessible(Z)V

    .line 138
    invoke-virtual {v0, p2}, Ljava/lang/reflect/Field;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1
    :try_end_10
    .catchall {:try_start_0 .. :try_end_10} :catchall_11

    return-object v1

    .line 139
    .end local v0  # "declaredField":Ljava/lang/reflect/Field;
    :catchall_11
    move-exception v0

    .line 140
    .local v0, "th":Ljava/lang/Throwable;
    invoke-virtual {v0}, Ljava/lang/Throwable;->printStackTrace()V

    .line 141
    const/4 v1, 0x0

    return-object v1
.end method

.method private varargs invokeMethod(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/String;[Ljava/lang/Object;[Ljava/lang/Class;)Ljava/lang/Object;
    .registers 8
    .param p1, "str"  # Ljava/lang/String;
    .param p2, "obj"  # Ljava/lang/Object;
    .param p3, "str2"  # Ljava/lang/String;
    .param p4, "objArr"  # [Ljava/lang/Object;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/lang/String;",
            "Ljava/lang/Object;",
            "Ljava/lang/String;",
            "[",
            "Ljava/lang/Object;",
            "[",
            "Ljava/lang/Class<",
            "*>;)",
            "Ljava/lang/Object;"
        }
    .end annotation

    .line 147
    .local p5, "clsArr":[Ljava/lang/Class;, "[Ljava/lang/Class<*>;"
    :try_start_0
    invoke-static {p1}, Ljava/lang/Class;->forName(Ljava/lang/String;)Ljava/lang/Class;

    move-result-object v0

    invoke-virtual {v0, p3, p5}, Ljava/lang/Class;->getDeclaredMethod(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;

    move-result-object v0

    .line 148
    .local v0, "declaredMethod":Ljava/lang/reflect/Method;
    const/4 v1, 0x1

    invoke-virtual {v0, v1}, Ljava/lang/reflect/Method;->setAccessible(Z)V

    .line 149
    invoke-virtual {v0, p2, p4}, Ljava/lang/reflect/Method;->invoke(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1
    :try_end_10
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_10} :catch_11

    return-object v1

    .line 150
    .end local v0  # "declaredMethod":Ljava/lang/reflect/Method;
    :catch_11
    move-exception v0

    .line 151
    .local v0, "e2":Ljava/lang/Exception;
    invoke-virtual {v0}, Ljava/lang/Exception;->printStackTrace()V

    .line 152
    const/4 v1, 0x0

    return-object v1
.end method

.method private invokeStaticMethod(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Class;[Ljava/lang/Object;)Ljava/lang/Object;
    .registers 7
    .param p1, "str"  # Ljava/lang/String;
    .param p2, "str2"  # Ljava/lang/String;
    .param p3, "clsArr"  # [Ljava/lang/Class;
    .param p4, "objArr"  # [Ljava/lang/Object;

    .line 158
    const/4 v0, 0x0

    :try_start_1
    invoke-static {p1}, Ljava/lang/Class;->forName(Ljava/lang/String;)Ljava/lang/Class;

    move-result-object v1

    invoke-virtual {v1, p2, p3}, Ljava/lang/Class;->getMethod(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;

    move-result-object v1

    invoke-virtual {v1, v0, p4}, Ljava/lang/reflect/Method;->invoke(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0
    :try_end_d
    .catchall {:try_start_1 .. :try_end_d} :catchall_e

    return-object v0

    .line 159
    :catchall_e
    move-exception v1

    .line 160
    .local v1, "th":Ljava/lang/Throwable;
    invoke-virtual {v1}, Ljava/lang/Throwable;->printStackTrace()V

    .line 161
    return-object v0
.end method

.method private setFieldOjbect(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V
    .registers 7
    .param p1, "str"  # Ljava/lang/String;
    .param p2, "str2"  # Ljava/lang/String;
    .param p3, "obj"  # Ljava/lang/Object;
    .param p4, "obj2"  # Ljava/lang/Object;

    .line 167
    :try_start_0
    invoke-static {p1}, Ljava/lang/Class;->forName(Ljava/lang/String;)Ljava/lang/Class;

    move-result-object v0

    invoke-virtual {v0, p2}, Ljava/lang/Class;->getDeclaredField(Ljava/lang/String;)Ljava/lang/reflect/Field;

    move-result-object v0

    .line 168
    .local v0, "declaredField":Ljava/lang/reflect/Field;
    const/4 v1, 0x1

    invoke-virtual {v0, v1}, Ljava/lang/reflect/Field;->setAccessible(Z)V

    .line 169
    invoke-virtual {v0, p3, p4}, Ljava/lang/reflect/Field;->set(Ljava/lang/Object;Ljava/lang/Object;)V
    :try_end_f
    .catch Ljava/lang/SecurityException; {:try_start_0 .. :try_end_f} :catch_18
    .catch Ljava/lang/ClassNotFoundException; {:try_start_0 .. :try_end_f} :catch_16
    .catch Ljava/lang/IllegalAccessException; {:try_start_0 .. :try_end_f} :catch_14
    .catch Ljava/lang/IllegalArgumentException; {:try_start_0 .. :try_end_f} :catch_12
    .catch Ljava/lang/NoSuchFieldException; {:try_start_0 .. :try_end_f} :catch_10

    .line 172
    .end local v0  # "declaredField":Ljava/lang/reflect/Field;
    goto :goto_1c

    .line 170
    :catch_10
    move-exception v0

    goto :goto_19

    :catch_12
    move-exception v0

    goto :goto_19

    :catch_14
    move-exception v0

    goto :goto_19

    :catch_16
    move-exception v0

    goto :goto_19

    :catch_18
    move-exception v0

    .line 171
    .local v0, "e":Ljava/lang/Exception;
    :goto_19
    invoke-virtual {v0}, Ljava/lang/Exception;->printStackTrace()V

    .line 173
    .end local v0  # "e":Ljava/lang/Exception;
    :goto_1c
    return-void
.end method

.method private setFieldValue(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;)Z
    .registers 7
    .param p1, "str"  # Ljava/lang/String;
    .param p2, "obj"  # Ljava/lang/Object;
    .param p3, "str2"  # Ljava/lang/String;
    .param p4, "obj2"  # Ljava/lang/Object;

    .line 177
    :try_start_0
    invoke-static {p1}, Ljava/lang/Class;->forName(Ljava/lang/String;)Ljava/lang/Class;

    move-result-object v0

    invoke-virtual {v0, p3}, Ljava/lang/Class;->getDeclaredField(Ljava/lang/String;)Ljava/lang/reflect/Field;

    move-result-object v0

    .line 178
    .local v0, "declaredField":Ljava/lang/reflect/Field;
    const/4 v1, 0x1

    invoke-virtual {v0, v1}, Ljava/lang/reflect/Field;->setAccessible(Z)V

    .line 179
    invoke-virtual {v0, p2, p4}, Ljava/lang/reflect/Field;->set(Ljava/lang/Object;Ljava/lang/Object;)V
    :try_end_f
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_f} :catch_10

    .line 180
    return v1

    .line 181
    .end local v0  # "declaredField":Ljava/lang/reflect/Field;
    :catch_10
    move-exception v0

    .line 182
    .local v0, "e":Ljava/lang/Exception;
    invoke-virtual {v0}, Ljava/lang/Exception;->printStackTrace()V

    .line 183
    const/4 v1, 0x0

    return v1
.end method


# virtual methods
.method public attachBaseContext(Landroid/content/Context;)V
    .registers 5
    .param p1, "context"  # Landroid/content/Context;

    .line 188
    invoke-direct {p0, p1}, Lcom/apksignaturekiller/HookApplication;->HookSign(Landroid/content/Context;)V

    .line 190
    :try_start_3
    const-string v0, "### Applicaton Data ###"

    invoke-static {v0}, Ljava/lang/Class;->forName(Ljava/lang/String;)Ljava/lang/Class;

    move-result-object v0

    const/4 v1, 0x0

    new-array v2, v1, [Ljava/lang/Class;

    invoke-virtual {v0, v2}, Ljava/lang/Class;->getConstructor([Ljava/lang/Class;)Ljava/lang/reflect/Constructor;

    move-result-object v0

    new-array v1, v1, [Ljava/lang/Object;

    invoke-virtual {v0, v1}, Ljava/lang/reflect/Constructor;->newInstance([Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/app/Application;

    iput-object v0, p0, Lcom/apksignaturekiller/HookApplication;->realApplication:Landroid/app/Application;

    .line 191
    invoke-direct {p0, p1}, Lcom/apksignaturekiller/HookApplication;->ReplaceApp(Landroid/content/Context;)V

    .line 192
    invoke-super {p0, p1}, Landroid/app/Application;->attachBaseContext(Landroid/content/Context;)V

    .line 193
    iget-object v0, p0, Lcom/apksignaturekiller/HookApplication;->realApplication:Landroid/app/Application;

    if-eqz v0, :cond_27

    .line 194
    invoke-virtual {p0, p1, v0}, Lcom/apksignaturekiller/HookApplication;->issieiej(Landroid/content/Context;Landroid/app/Application;)V
    :try_end_27
    .catchall {:try_start_3 .. :try_end_27} :catchall_28

    .line 198
    :cond_27
    goto :goto_2c

    .line 196
    :catchall_28
    move-exception v0

    .line 197
    .local v0, "th":Ljava/lang/Throwable;
    invoke-virtual {v0}, Ljava/lang/Throwable;->printStackTrace()V

    .line 199
    .end local v0  # "th":Ljava/lang/Throwable;
    :goto_2c
    return-void
.end method

.method public invoke(Ljava/lang/Object;Ljava/lang/reflect/Method;[Ljava/lang/Object;)Ljava/lang/Object;
    .registers 10
    .param p1, "obj"  # Ljava/lang/Object;
    .param p2, "method"  # Ljava/lang/reflect/Method;
    .param p3, "objArr"  # [Ljava/lang/Object;
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Throwable;
        }
    .end annotation

    .line 203
    invoke-virtual {p2}, Ljava/lang/reflect/Method;->getName()Ljava/lang/String;

    move-result-object v0

    const-string v1, "getPackageInfo"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_4d

    .line 204
    const/4 v0, 0x0

    aget-object v0, p3, v0

    check-cast v0, Ljava/lang/String;

    .line 205
    .local v0, "str":Ljava/lang/String;
    const/4 v1, 0x1

    aget-object v1, p3, v1

    check-cast v1, Ljava/lang/Integer;

    invoke-virtual {v1}, Ljava/lang/Integer;->intValue()I

    move-result v1

    iget v2, p0, Lcom/apksignaturekiller/HookApplication;->GET_SIGNATURES:I

    and-int/2addr v1, v2

    if-eqz v1, :cond_4d

    iget-object v1, p0, Lcom/apksignaturekiller/HookApplication;->appPkgName:Ljava/lang/String;

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_4d

    .line 206
    iget-object v1, p0, Lcom/apksignaturekiller/HookApplication;->base:Ljava/lang/Object;

    invoke-virtual {p2, v1, p3}, Ljava/lang/reflect/Method;->invoke(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Landroid/content/pm/PackageInfo;

    .line 207
    .local v1, "packageInfo":Landroid/content/pm/PackageInfo;
    iget-object v2, p0, Lcom/apksignaturekiller/HookApplication;->sign:[[B

    array-length v2, v2

    new-array v2, v2, [Landroid/content/pm/Signature;

    iput-object v2, v1, Landroid/content/pm/PackageInfo;->signatures:[Landroid/content/pm/Signature;

    .line 208
    const/4 v2, 0x0

    .local v2, "i":I
    :goto_37
    iget-object v3, v1, Landroid/content/pm/PackageInfo;->signatures:[Landroid/content/pm/Signature;

    array-length v3, v3

    if-ge v2, v3, :cond_4c

    .line 209
    iget-object v3, v1, Landroid/content/pm/PackageInfo;->signatures:[Landroid/content/pm/Signature;

    new-instance v4, Landroid/content/pm/Signature;

    iget-object v5, p0, Lcom/apksignaturekiller/HookApplication;->sign:[[B

    aget-object v5, v5, v2

    invoke-direct {v4, v5}, Landroid/content/pm/Signature;-><init>([B)V

    aput-object v4, v3, v2

    .line 208
    add-int/lit8 v2, v2, 0x1

    goto :goto_37

    .line 211
    .end local v2  # "i":I
    :cond_4c
    return-object v1

    .line 214
    .end local v0  # "str":Ljava/lang/String;
    .end local v1  # "packageInfo":Landroid/content/pm/PackageInfo;
    :cond_4d
    iget-object v0, p0, Lcom/apksignaturekiller/HookApplication;->base:Ljava/lang/Object;

    invoke-virtual {p2, v0, p3}, Ljava/lang/reflect/Method;->invoke(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    return-object v0
.end method

.method public issieiej(Landroid/content/Context;Landroid/app/Application;)V
    .registers 9
    .param p1, "context"  # Landroid/content/Context;
    .param p2, "application"  # Landroid/app/Application;

    .line 218
    if-eqz p2, :cond_2a

    .line 220
    :try_start_2
    const-string v0, "android.content.ContextWrapper"

    invoke-static {v0}, Ljava/lang/Class;->forName(Ljava/lang/String;)Ljava/lang/Class;

    move-result-object v0

    const-string v1, "attachBaseContext"

    const/4 v2, 0x1

    new-array v3, v2, [Ljava/lang/Class;

    const-string v4, "android.content.Context"

    invoke-static {v4}, Ljava/lang/Class;->forName(Ljava/lang/String;)Ljava/lang/Class;

    move-result-object v4

    const/4 v5, 0x0

    aput-object v4, v3, v5

    invoke-virtual {v0, v1, v3}, Ljava/lang/Class;->getDeclaredMethod(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;

    move-result-object v0

    .line 221
    .local v0, "declaredMethod":Ljava/lang/reflect/Method;
    invoke-virtual {v0, v2}, Ljava/lang/reflect/Method;->setAccessible(Z)V

    .line 222
    new-array v1, v2, [Ljava/lang/Object;

    aput-object p1, v1, v5

    invoke-virtual {v0, p2, v1}, Ljava/lang/reflect/Method;->invoke(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
    :try_end_24
    .catchall {:try_start_2 .. :try_end_24} :catchall_26

    .line 225
    nop

    .end local v0  # "declaredMethod":Ljava/lang/reflect/Method;
    goto :goto_2a

    .line 223
    :catchall_26
    move-exception v0

    .line 224
    .local v0, "th":Ljava/lang/Throwable;
    invoke-virtual {v0}, Ljava/lang/Throwable;->printStackTrace()V

    .line 227
    .end local v0  # "th":Ljava/lang/Throwable;
    :cond_2a
    :goto_2a
    return-void
.end method

.method public onCreate()V
    .registers 2

    .line 230
    invoke-super {p0}, Landroid/app/Application;->onCreate()V

    .line 231
    const-string v0, "### Applicaton Data ###"

    invoke-direct {p0, v0, p0}, Lcom/apksignaturekiller/HookApplication;->ChangeApplication(Ljava/lang/String;Landroid/content/Context;)Landroid/app/Application;

    move-result-object v0

    .line 232
    .local v0, "ChangeApplication":Landroid/app/Application;
    if-eqz v0, :cond_e

    .line 233
    invoke-virtual {v0}, Landroid/app/Application;->onCreate()V

    .line 235
    :cond_e
    return-void
.end method
