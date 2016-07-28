package com.gyx.projectconstruct.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import junit.framework.Assert;
import android.content.Context;
import android.util.Log;

import com.gyx.projectconstruct.BuildConfig;

public class L {
	private static boolean debug = BuildConfig.DEBUG;
	private static final String mTag = "Just GYX Say So : ";

	public static void onCreate(Context con) {
		String className = con.getClass().getSimpleName();
		if (debug)
			Log.i(className, className + " onCreate");
	}

	public static void onStart(Context con) {
		String className = con.getClass().getSimpleName();
		if (debug)
			Log.i(className, className + " onStart");
	}

	public static void onResume(Context con) {
		String className = con.getClass().getSimpleName();
		if (debug)
			Log.i(className, className + " onResume");
	}

	public static void onPause(Context con) {
		String className = con.getClass().getSimpleName();
		if (debug)
			Log.i(className, className + " onPause");
	}

	public static void onStop(Context con) {
		String className = con.getClass().getSimpleName();
		if (debug)
			Log.i(className, className + " onStop");
	}

	public static void onDestory(Context con) {
		String className = con.getClass().getSimpleName();
		if (debug)
			Log.i(className, className + " onDistory");
	}

	public static void onReStart(Context con) {
		String className = con.getClass().getSimpleName();
		if (debug)
			Log.i(className, className + " onReStart");
	}

	public static void i(String tag, String format, Object... args) {
		if (debug)
			Log.i(tag, String.format(format, args));
	}

	public static void i(String tag, String msg) {
		if (debug)
			Log.i(tag, msg);
	}

	public static void i(String format, Object... args) {
		if (debug)
			i(mTag, format, args);
	}

	public static void i(String msg) {
		if (debug)
			Log.i(mTag, msg);
	}

	public static void e(String tag, String format, Object... args) {
		if (debug)
			Log.e(tag, String.format(format, args));
	}

	public static void e(String format, Object... args) {
		if (debug)
			e(mTag, format, args);
	}

	public static void e(String tag, String msg) {
		if (debug)
			Log.e(tag, msg);
	}

	public static void e(String msg) {
		if (debug)
			Log.e(mTag, msg);
	}

	public static void d(String tag, String msg) {
		if (debug)
			Log.d(tag, msg);
	}

	public static void assertEquals(String expected, String actual) {
		if (debug) {
			Assert.assertEquals(expected, actual);
		}
	}

	public static void assertEquals(boolean expected, boolean actual) {
		if (debug) {
			Assert.assertEquals(expected, actual);
		}
	}

	public static void assertEquals(byte expected, byte actual) {
		if (debug) {
			Assert.assertEquals(expected, actual);
		}
	}

	public static void assertEquals(char expected, char actual) {
		if (debug) {
			Assert.assertEquals(expected, actual);
		}
	}

	public static void assertEquals(int expected, int actual) {
		if (debug) {
			Assert.assertEquals(expected, actual);
		}
	}

	public static void assertEquals(long expected, long actual) {
		if (debug) {
			Assert.assertEquals(expected, actual);
		}
	}

	public static void assertEquals(short expected, short actual) {
		if (debug) {
			Assert.assertEquals(expected, actual);
		}
	}

	public static void assertEquals(double expected, double actual) {
		if (debug) {
			Assert.assertEquals(expected, actual);
		}
	}

	public static void assertEquals(float expected, float actual) {
		if (debug) {
			Assert.assertEquals(expected, actual);
		}
	}

	public static void assertEquals(String mssage, String expected, String actual) {
		if (debug) {
			Assert.assertEquals(mssage, expected, actual);
		}
	}

	public static void assertEquals(String mssage, boolean expected, boolean actual) {
		if (debug) {
			Assert.assertEquals(mssage, expected, actual);
		}
	}

	public static void assertEquals(String mssage, byte expected, byte actual) {
		if (debug) {
			Assert.assertEquals(mssage, expected, actual);
		}
	}

	public static void assertEquals(String mssage, char expected, char actual) {
		if (debug) {
			Assert.assertEquals(mssage, expected, actual);
		}
	}

	public static void assertEquals(String mssage, int expected, int actual) {
		if (debug) {
			Assert.assertEquals(mssage, expected, actual);
		}
	}

	public static void assertEquals(String mssage, long expected, long actual) {
		if (debug) {
			Assert.assertEquals(mssage, expected, actual);
		}
	}

	public static void assertEquals(String mssage, short expected, short actual) {
		if (debug) {
			Assert.assertEquals(mssage, expected, actual);
		}
	}

	public static void assertEquals(String mssage, double expected, double actual) {
		if (debug) {
			Assert.assertEquals(mssage, expected, actual);
		}
	}

	public static void assertEquals(String mssage, float expected, float actual) {
		if (debug) {
			Assert.assertEquals(mssage, expected, actual);
		}
	}

	public static void assertNull(Object object) {
		if (debug) {
			Assert.assertNull(object);
		}
	}

	public static void assertNull(String msg, Object object) {
		if (debug) {
			Assert.assertNull(msg, object);
		}
	}

	public static void assertTrue(boolean condition) {
		if (debug) {
			Assert.assertTrue(condition);
		}
	}

	public static void assertTrue(String msg, boolean condition) {
		if (debug) {
			Assert.assertTrue(msg, condition);
		}
	}

	public static void assertFalse(boolean condition) {
		if (debug) {
			Assert.assertFalse(condition);
		}
	}

	public static void assertFalse(String msg, boolean condition) {
		if (debug) {
			Assert.assertFalse(msg, condition);
		}
	}

	public static void assertNotNull(Object object) {
		if (debug) {
			Assert.assertNotNull(object);
		}
	}

	public static void assertNotNull(String msg, Object object) {
		if (debug) {
			Assert.assertNotNull(msg, object);
		}
	}

	public static void assertSame(Object expected, Object actual) {
		if (debug) {
			Assert.assertSame(expected, actual);
		}
	}

	public static void assertSame(String msg, Object expected, Object actual) {
		if (debug) {
			Assert.assertSame(msg, expected, actual);
		}
	}

	public static void assertNotSame(Object expected, Object actual) {
		if (debug) {
			Assert.assertNotSame(expected, actual);
		}
	}

	public static void assertNotSame(String msg, Object expected, Object actual) {
		if (debug) {
			Assert.assertNotSame(msg, expected, actual);
		}
	}

	public static void assertShouldNotBeExed() {
		assertNotNull("this code should not be exed, please check the logic", null);
	}
	
	public static void writeFile(File file, String msg) {
		FileWriter fw = null;
		try {
			File dir = file.getParentFile(); 
			if(!dir.exists()) {
				dir.mkdirs();
			}
			if(!file.exists()) {
				file.createNewFile();
			}
			fw = new FileWriter(file, true);
			fw.append(msg);
			fw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(fw != null)
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
