package com.biz.a1;

import com.code5.fw.data.InitYaml;

/**
 * @author zero
 *
 *         인터페이스와 구현클래스가 1:1 관계일때는 인터페이스는 필요하지 않습니다.
 */
class A {

	void execute() {

		// A 가 B 의 기능을 사용한다.
		// A 가 B 에 의존한다.

		// Ripple Effect(파급효과), B의 오류 또는 기능의 문제가 A 실행 결과를 이상하게 만듬
		// 낮은결합도가 필요

		// Side Effect(부작용), B의 오류 또는 기능을 개선했지만 여전히 A 실행 결과가 이상함
		// 회귀테스트가 필요

		// A 가 B 의 기능을 사용할 때
		// B 에서 발생한 문제는 A 로 전달, 파급효과가 발생
		// 파급효과는 항상 발생하는 문제
		// 파급효과가 발생했을때 제거하기 위한 비용을 줄이기 위한 노력이 바로 낮은 결합도

		// A 가 B 의 기능을 사용하기 위해 준비
		B 객체 = new B();

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
		// 해결 : 실행시점에 기능과 데이터를 결정, 파급효과 해결을 위해 A 도 같이 수정
		boolean isDev = InitYaml.get().is("isDev");
		if (isDev) {
			객체.민감한기능_stub();
		} else {
			객체.민감한기능();
		}

		// A 가 B 의 기능을 사용한다.
		// B 의 코드를 A 에서 사용하고 컴파일 한다.
		// 내용결합, 가장 높은 결합

		// 낮은 결합도는 파급효과가 발생했을때 제거하는 비용을 줄여주지만
		// (컴피일 없이)
		// 내용 결합은 파급효과가 발생했을때 높은 제거 비용이 필요
		// (컴파일이 필요)

		// 파급효과를 제거하는 비용을 줄이기 위해 스템프 결합도를 사용한다면?
	}

}

class B {

	void 문제가있음() {
	}

	private int 잘만들어진기능에서사용하는데이터 = 0;

	int 잘만들어짐() {
		return 잘만들어진기능에서사용하는데이터++;
	}

	int 잘만들어짐(int i) {
		잘만들어진기능에서사용하는데이터 = i;
		잘만들어진기능에서사용하는데이터++;
		return 잘만들어진기능에서사용하는데이터;

	}

	void 민감한기능() {
		// 실제 기능
	}

	void 민감한기능_stub() {
		// stub 테스트를 위한 가상의 기능
	}
}