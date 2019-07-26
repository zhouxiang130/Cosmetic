package com.yj.cosmetics.cosmetic;

import android.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class ExampleInstrumentedTest {


	private static final String TAG = "ProxyTest";

	public static Object LoadProxy(final Object o) throws Exception {
		//通过接口class 对象创建代理class对象
		Class<?> proxyClass = Proxy.getProxyClass(o.getClass().getClassLoader(), o.getClass().getInterfaces());

		//获取代码Class对象的有参构造方法
		Constructor<?> constructor = proxyClass.getConstructor(InvocationHandler.class);
		Object objects = constructor.newInstance(new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				Log.i(TAG, "invoke: ");
				Object invoke = method.invoke(o, args);
				return invoke;
			}
		});
		return objects;
	}

	public static void main(String[] args) throws Exception {
		ILogin proxy = (ILogin) LoadProxy(new UserLogin());
		proxy.userLogin();
	}
}
