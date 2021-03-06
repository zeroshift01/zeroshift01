package com.biz.a2;

import com.code5.fw.data.InitYaml;

/**
 * @author zero
 *
 *         인터페이스와 구현클래스가 1:1 관계일때는 인터페이스는 필요하지 않습니다.
 */
class A {

	void execute() {

		// 낮은 결합도는 파급효과가 발생했을때 제거하는 비용이 줄어듬

		// 파급효과를 줄이기 위해 인터페이스를 사용

		// 인터페이스
		// 1) 기능의 틀을 정의하고
		// 2) 틀에 맞는 여러개의 구현 클래스를 만든뒤
		// 3) 실행시점에 그 중 하나를 선택해 추상화 해서 사용

		// 교체의 파급효과가 발생했을때 쉽게 제거가 가능

		B 객체 = B.createB();

		// 문제1
		// 예 : 오류, 지연, 어려움
		// 해결 : 수정, A 수정 없이 파급효과 해결
		객체.문제가있음();

		// 문제2
		// 예 : 요구사항, 리펙토링
		// 해결 : 기능 추가 , 파급효과 해결을 위해 A 도 같이 수정
		객체.잘만들어짐();
		객체.잘만들어짐(0);

		// 문제3
		// 예 : 계좌이체, 이메일전송
		// 해결 : 실행시점에 기능과 데이터를 결정, 파급효과가 발생했을때 쉽게 제거
		객체.민감한기능();

		// 인터페이스의 가장 큰 문제
		// 1) 중복코드
		// 2) 정보은닉 어려움

		// 중복코드를 해결하기 위한 노력은 의미가 없음

		// default, static : 객체지향적이지 않음(데이터가 없고 기능만 있음)
		// 유틸 클래스 : 응집도 관점에서 효과적이지 못함, 코드가 복잡해짐
		// 클래스 분리 : 교체가 필요한 기능만 인터페이스로 이동, 코드가 복잡해짐

		// 파급효과가 발생했을때 제거하는 비용을 줄이기 위해
		// 낮은 결합도를 제공하는 인터페이스를 사용했지만

		// 개발 생산성이 낮아지는 문제가 발생

		// 1) 파급효과와 이를 해결하기 위한 비용은 그대로 남아 있음 (내용 결합도 그대로)
		// 2) SW 특성 순응성, 요구사항에 따라 인터페이스와 구현 클래스도 동시 변경
		// 3) 인터페이스와 구현클래스가 1:N 인 요구사항은 없음

		// 인터페이스와 구현클래스가 1:1 관계일때는 인터페이스를 제거

	}

}

// 1) 기능의 틀을 정의 하고
interface B {

	public static B createB() {

		// 3) 실행시점에 그 중 하나를 선택해 추상화 해서 사용

		boolean isDev = InitYaml.get().is("isDev");

		B 객체 = null;
		if (isDev) {
			객체 = new BDvImpl();
		} else {
			객체 = new BPrImpl();
		}

		return 객체;

	}

	void 문제가있음();

	int 잘만들어짐();

	int 잘만들어짐(int i);

	void 민감한기능();

}

// 2) 정한 틀을 사용할 수 있는 여러개의 클래스를 만든뒤
class BDvImpl implements B {

	public void 문제가있음() {
	}

	private int 잘만들어진기능에서사용하는데이터 = 0;

	public int 잘만들어짐() {
		return 잘만들어진기능에서사용하는데이터++;
	}

	public int 잘만들어짐(int i) {
		잘만들어진기능에서사용하는데이터 = i;
		잘만들어진기능에서사용하는데이터++;
		return 잘만들어진기능에서사용하는데이터;

	}

	public void 민감한기능() {
		// stub
	}

}

// 2) 정한 틀을 사용할 수 있는 여러개의 클래스를 만든뒤
class BPrImpl implements B {

	public void 문제가있음() {
	}

	private int 잘만들어진기능에서사용하는데이터 = 0;

	public int 잘만들어짐() {
		return 잘만들어진기능에서사용하는데이터++;
	}

	public int 잘만들어짐(int i) {
		잘만들어진기능에서사용하는데이터 = i;
		잘만들어진기능에서사용하는데이터++;
		return 잘만들어진기능에서사용하는데이터;
	}

	public void 민감한기능() {
		// 실제 동작
	}

}
