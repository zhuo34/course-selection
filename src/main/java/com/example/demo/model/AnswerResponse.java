package com.example.demo.model;

public class AnswerResponse {
	private int isRight;
	private int a;
	private int b;

	public AnswerResponse(int right) {
		setIsRight(right);
		a = (int)(Math.random() * 1000);
		b = (int)(Math.random() * 1000);
	}

	public void setIsRight(int right) {
		isRight = right;
	}

	public int getIsRight() {
		return isRight;
	}

	public int getA() {
		return a;
	}

	public int getB() {
		return b;
	}
}
