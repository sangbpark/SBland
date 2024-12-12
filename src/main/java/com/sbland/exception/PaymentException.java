package com.sbland.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public class PaymentException extends RuntimeException  {
	private final String impUid;
	private final String message;

}
