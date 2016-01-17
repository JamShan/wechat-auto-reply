package me.lishuo.wechat.util;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author lis
 *
 */
public class CommonUtils {
	public static boolean isNull(Object obj) {
		return obj == null;
	}

	public static <T> boolean isEmptyArray(T[] array) {
		return (array == null) || (array.length == 0);
	}

	public static boolean isEmptyString(String str) {
		return (str == null) || (str.length() == 0);
	}

	public static boolean isAlphaNumeric(char c) {
		return (isLetter(c)) || (isNumber(c));
	}

	public static boolean isAlphaNumeric(String inStr) {
		for (int i = 0; i < inStr.length(); i++) {
			if ((!isLetter(inStr.charAt(i))) && (!isNumber(inStr.charAt(i)))) {
				return false;
			}
		}
		return true;
	}

	public static boolean isLetter(char c) {
		c = regularize(c);

		return ((c >= 'a') && (c <= 'z')) || ((c >= 'A') && (c <= 'Z'));
	}

	public static boolean isLetter(String inStr) {
		for (int i = 0; i < inStr.length(); i++) {
			if (!isLetter(inStr.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public static char regularize(char input) {
		if (input == '　') {
			input = ' ';
		} else if ((input > 65280) && (input < 65375)) {
			input = (char) (input - 65248);
		}
		return input;
	}

	public static boolean isNumber(char c) {
		c = regularize(c);
		return (c >= '0') && (c <= '9');
	}

	public static boolean isNumber(String inStr) {
		for (int i = 0; i < inStr.length(); i++) {
			if (!isNumber(inStr.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public static boolean isChineseLetter(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);

		return (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS)
				|| (ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS)
				|| (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A)
				|| (ub == Character.UnicodeBlock.GENERAL_PUNCTUATION)
				|| (ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION)
				|| (ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS);
	}

	public static boolean isPunctuation(String inStr) {
		for (int i = 0; i < inStr.length(); i++) {
			char c = inStr.charAt(i);
			Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
			if ((ub != Character.UnicodeBlock.GENERAL_PUNCTUATION)
					&& (ub != Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION)
					&& (ub != Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS)
					&& (ub != Character.UnicodeBlock.CJK_COMPATIBILITY_FORMS)
					&& (ub != Character.UnicodeBlock.VERTICAL_FORMS) && ((c < '!') || (c > '/'))
					&& ((c < ':') || (c > '@')) && ((c <= 'Z') || (c >= 'a')) && ((c < '{') || (c > '~'))) {
				return false;
			}
		}
		return true;
	}

	public static boolean isEmptySpace(char c) {
		return (c == ' ') || (c == '\t');
	}

	public static boolean isEmptySpace(String inStr) {
		for (int i = 0; i < inStr.length(); i++) {
			if (!isEmptySpace(inStr.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public static <T extends Comparable<T>> List<T> uniqSort(List<T> inList) {
		if ((inList == null) || (inList.size() <= 1)) {
			return inList;
		}
		HashSet uniqContainer = new HashSet(inList);
		List result = new ArrayList(uniqContainer);
		Collections.sort(result);
		return result;
	}

	public static <T extends Comparable<T>> List<T> uniqSort(List<T> inList, int limit) {
		if ((inList == null) || (inList.size() <= 1)) {
			return inList;
		}
		HashSet uniqContainer = new HashSet(inList);
		List result = new ArrayList(uniqContainer);
		return sort(result, limit);
	}

	public static <T extends Comparable<T>> List<T> sort(List<T> inList) {
		if ((inList == null) || (inList.size() <= 1)) {
			return inList;
		}
		Collections.sort(inList);
		return inList;
	}

	public static <T extends Comparable<T>> List<T> sort(List<T> inList, int limit) {
		if ((inList == null) || (inList.size() <= 1)) {
			return inList;
		}
		Collections.sort(inList);
		if (limit >= inList.size()) {
			return inList;
		}
		return inList.subList(0, limit);
	}

	public static byte[] doubleToBytes(double value) {
		byte[] bytes = new byte[8];
		ByteBuffer.wrap(bytes).putDouble(value);
		return bytes;
	}

	public static double bytesToDouble(byte[] bytes) {
		return ByteBuffer.wrap(bytes).getDouble();
	}

	public static byte[] longToBytes(long x) {
		ByteBuffer buffer = ByteBuffer.allocate(8);
		buffer.putLong(x);
		return buffer.array();
	}

	public static long bytesToLong(byte[] bytes) {
		ByteBuffer buffer = ByteBuffer.allocate(8);
		buffer.put(bytes);
		buffer.flip();
		return buffer.getLong();
	}

	public static long stringToLong(String inStr, long defaultValue) {
		try {
			return Long.valueOf(inStr).longValue();
		} catch (NumberFormatException e) {
		}
		return defaultValue;
	}

	public static double formatDouble(double num, int index) {
		double result = 0;
		double temp = Math.pow(10, index);
		result = Math.round(num * temp) / temp;
		return result;
	}

	public static String encodeByMD5(String originstr) {
		String result = null;
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		if (originstr != null) {
			try {
				MessageDigest md = MessageDigest.getInstance("MD5");
				byte[] source = originstr.getBytes("utf-8");
				md.update(source);
				byte[] tmp = md.digest();

				char[] str = new char[32];
				for (int i = 0, j = 0; i < 16; i++) {
					byte b = tmp[i];
					str[j++] = hexDigits[b >>> 4 & 0xf];
					str[j++] = hexDigits[b & 0xf];
				}
				result = new String(str);

			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static boolean isMobileNumber(String mobiles) {
		Pattern p = Pattern.compile("^(1[3|4|5|7|8][0-9])\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}
}
