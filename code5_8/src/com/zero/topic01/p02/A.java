package com.zero.topic01.p02;

class A {

	void method() {

		B b = new B();

		// �ı�ȿ���� �߻������� �����ϴ� ���

		// 1. ���� : ����, ���ɹ���, ������
		b.method();

		// 2. �߰� : ���ο� ����� �߰�, �������̵�, ���ο� �޼ҵ�
		b.method("p");
		b.method2();

		// 3. ��ü : ���� ��ɰ� ���ο� ����� ���ÿ� ���
		b.method3();

	}

}

class B {

	void method() {
		// 1. ���� : ���� ����, ��� ����
	}

	void method(String p) {
		// 2. �߰� : �������̵�
	}

	void method2() {
		// 2. �߰� : ���ο� �޼ҵ�
	}

	// 3. ��ü : ���� ��ɵ� ����ϰ� ���ο� ��ɵ� ���� ���
	void method3() {

		// ����Ŭ ����̹�/MySql ����̹� -> JDBC �߻�ȭ
		// ArrayList / LinkedList -> List �߻�ȭ

		// ������ ��ü�� ������ ��ȸ�� ����.
		boolean isDv = true;
		if (isDv) {
			// test stub
			return;
		}

		// ���������ڵ�
	}
}