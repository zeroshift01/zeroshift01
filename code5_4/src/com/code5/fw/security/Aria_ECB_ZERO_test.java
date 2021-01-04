package com.code5.fw.security;

/**
 * @author seuk
 *
 */
public class Aria_ECB_ZERO_test {

	public static void main(String[] xxx) throws Exception {

		byte[] key = new byte[16];

		Aria_ECB_ZERO aria_ECB_ZERO = new Aria_ECB_ZERO(key);

		byte[] plan = new byte[16];

		byte[] enc = aria_ECB_ZERO.encrypt(plan);

		byte[] dec = aria_ECB_ZERO.encrypt(plan);

		System.out.println(plan.length);
		System.out.println(enc.length);
		System.out.println(dec.length);

	}

}
