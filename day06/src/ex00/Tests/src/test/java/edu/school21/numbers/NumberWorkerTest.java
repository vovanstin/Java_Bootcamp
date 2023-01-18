package edu.school21.numbers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

public class NumberWorkerTest {
	
	NumberWorker numberWorker;

    @BeforeEach
    void beforeEach() {
        numberWorker = new NumberWorker();
    }

	@ParameterizedTest
	@ValueSource(ints = {2, 3, 5, 11, 17, 2147483647})
	void isPrimeForPrimes(int number) {
		assertTrue(numberWorker.isPrime(number));
	}

	@ParameterizedTest
	@ValueSource(ints = {4, 8, 18, 42, 168})
	void isPrimeForNotPrimes(int number) {
		assertFalse(numberWorker.isPrime(number));
	}

	@ParameterizedTest
	@ValueSource(ints = {1, -193, 0, -100023, -76})
	void isPrimeForIncorrectNumbers(int number) {
		assertThrows(IllegalNumberException.class, () -> {
			numberWorker.isPrime(number);
		});
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/data.csv")
	void digitsSumShouldGenerateTheExpectedNumberValueCSVFile(int input, int expected) {
		assertEquals(expected, numberWorker.digitsSum(input));
	}
}
