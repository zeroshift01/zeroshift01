package com.code5.fw.security;

import org.junit.Test;

/**
 * @author zero
 *
 */
public class CryptPin_test {

	@Test
	public void test_01() {

		String A0_PIN = CryptPin.cryptPin("abcd1234", "id_A0");

		String U0_PIN = CryptPin.cryptPin("abcd1234", "id_U0");

		System.out.println(A0_PIN);
		System.out.println(U0_PIN);

	}

}