package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MoneyTest {
	Currency SEK, DKK, NOK, EUR;
	Money SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100;

	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
		SEK100 = new Money(10000, SEK);
		EUR10 = new Money(1000, EUR);
		SEK200 = new Money(20000, SEK);
		EUR20 = new Money(2000, EUR);
		SEK0 = new Money(0, SEK);
		EUR0 = new Money(0, EUR);
		SEKn100 = new Money(-10000, SEK);
	}

	@Test
	public void testGetAmount() {
		// Test if getAmount() correctly returns the amount of money.
		assertEquals(10000, SEK100.getAmount().intValue());
		assertEquals(1000, EUR10.getAmount().intValue());
	}

	@Test
	public void testGetCurrency() {
		// Test if getCurrency() correctly returns the currency of the money.
		assertEquals(SEK, SEK100.getCurrency());
		assertEquals(EUR, EUR10.getCurrency());
	}

	@Test
	public void testToString() {
		// Test if toString() correctly formats the string representation of money.
		assertEquals("100.00 SEK", SEK100.toString());
		assertEquals("10.00 EUR", EUR10.toString());
	}

	@Test
	public void testGlobalValue() {
		// Test if universalValue() correctly converts the money to universal currency.
		assertEquals(1500, SEK100.universalValue().intValue());
		assertEquals(1500, EUR10.universalValue().intValue());
	}

	@Test
	public void testEqualsMoney() {
		// Test if equals() correctly identifies if two Money objects have the same and
		// different amounts
		assertTrue(SEK100.equals(new Money(10000, SEK)));
		assertFalse(SEK100.equals(EUR0));
	}

	@Test
	public void testAdd() {
		// Test if add() correctly adds the amount of one Money to another
		Money sum = SEK100.add(EUR10);
		int expectedSumUniversal = SEK100.universalValue() + EUR10.universalValue();
		int expectedSumInSEK = SEK.valueInThisCurrency(expectedSumUniversal, SEK);
		assertEquals(expectedSumInSEK, sum.getAmount().intValue());
	}

	@Test
	public void testSub() {
		// Test if sub() correctly subtracts the amount of one Money from another
		Money difference = SEK200.sub(EUR10);
		int expectedDiffUniversal = SEK200.universalValue() - EUR10.universalValue();
		int expectedDiffInSEK = SEK.valueInThisCurrency(expectedDiffUniversal, SEK);
		assertEquals(expectedDiffInSEK, difference.getAmount().intValue());

	}

	@Test
	public void testIsZero() {
		// Test if isZero() correctly identifies Money objects with amount equal zero
		assertTrue(SEK0.isZero());
		assertFalse(SEK100.isZero());
	}

	@Test
	public void testNegate() {
		// Test if negate() correctly negates the amount of Money
		assertEquals(-10000, SEK100.negate().getAmount().intValue());
		assertEquals(10000, SEKn100.negate().getAmount().intValue());
	}

	@Test
	public void testCompareTo() {
		// Test if compareTo() correctly compares two Money objects based on their
		// universal value
		assertTrue(SEK100.compareTo(EUR10) == 0);
		assertTrue(SEK200.compareTo(EUR10) > 0);
		assertTrue(SEKn100.compareTo(SEK100) < 0);
	}
}