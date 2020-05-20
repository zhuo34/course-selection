package com.example.demo.model;

public class AnswerRequest {
	private int a;
	private int b;
	private int c;

	public int getA() {
		return a;
	}

	public void setA(int a) {
		this.a = a;
	}

	public void setB(int b) {
		this.b = b;
	}

	public int getB() {
		return b;
	}

	public int getC() {
		return c;
	}

	public void setC(int c) {
		this.c = c;
	}

	public int isRight() {
		return a + b - c;
	}
}
