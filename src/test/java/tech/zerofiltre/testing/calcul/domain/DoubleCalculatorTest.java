package tech.zerofiltre.testing.calcul.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

   class DoubleCalculatorTest {
	private Calculator calculatorUnderTest;

	@BeforeEach
	   void initCalculator() {
		calculatorUnderTest = new Calculator();
	}
	
	@Test
	@Disabled("Test ambigu et hors limite du type double")
	   void subTwoDoubleNumbers_shouldReturnsTheCorrectAnswer() {
		// GIVEN
		
		// WHEN
		double result = calculatorUnderTest.sub(1.0000000001, 1.0);
		
		// THEN
		if(result != 0.0000000001) {
			throw new AssertionError(String.format("Erreur :\nAttendu :  0,0000000001\n Résultat : %.10f", result));
		}
		
	}
}
