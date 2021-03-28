package com.zero.topic01.p07;

class A {

	void method() {

		// �������̽��� ����Ŭ������ 1:1 ����

		// A -> B <- B_Impl

		// �ı�ȿ���� �߻������� �����ϴ� ���

		// 1. ����(�������̽� ����� ����) : ���� ����, ��� ����
		// 2. �߰�(�����) : ���ο� ����� �߰�, �������̵�, ���ο� �޼ҵ�
		// 3. ��ü(����� ��ȸ�� ����) : ���� ��ɰ� ���ο� ����� ���ÿ� ���

		// �������̽��� ����Ѵٰ� �ص� �ı�ȿ�� �����ϴ� ����� ���� �ʴ´�!
		// ������ ����!

		B b = new B_Impl();
		b.method();

		b.method("p");
		b.method2();
	}

}

interface B {
	public void method();

	public void method(String p);

	public void method2();

	// public void method3();
}

class B_Impl implements B {

	public void method() {
		// 1. ���� : ���� ����, ��� ����
	}

	public void method(String p) {
		// 2. �߰� : �������̵�
	}

	public void method2() {
		// 2. �߰� : ���ο� �޼ҵ�
	}

}