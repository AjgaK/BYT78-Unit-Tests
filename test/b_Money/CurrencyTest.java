package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CurrencyTest {
	Currency SEK, DKK, NOK, EUR;

	@Before
	public void setUp() throws Exception {
		/* Setup currencies with exchange rates */
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
	}

	@Test
	public void testGetName() {
		// Test if getName() correctly returns the name of the currency
		assertEquals("SEK", SEK.getName());
		assertEquals("DKK", DKK.getName());
		assertEquals("EUR", EUR.getName());
	}

	@Test
	public void testGetRate() {
		// Test if getRate() correctly returns the exchange rate of the currency
		assertEquals(0.15, SEK.getRate(), 0.001);
		assertEquals(0.20, DKK.getRate(), 0.001);
		assertEquals(1.5, EUR.getRate(), 0.001);
	}

	@Test
	public void testSetRate() {
		// Test if setRate() correctly updates the currency's exchange rate
		SEK.setRate(0.2);
		assertEquals(0.2, SEK.getRate(), 0.001);
		// Resetting to original rate for consistency
		SEK.setRate(0.15);
	}

	@Test
	public void testGlobalValue() {
		// Test if universalValue() correctly converts the given amount to the universal
		// currency using the currency's rate
		assertEquals(30007, (int) SEK.universalValue(200050));
		assertEquals(40010, (int) DKK.universalValue(200050));
		assertEquals(300075, (int) EUR.universalValue(200050));
	}

	@Test
	public void testValueInThisCurrency() {
		// Test if valueInThisCurrency() correctly converts an amount from another
		// currency to this currency
		assertEquals(2000500, (int) SEK.valueInThisCurrency(200050, EUR));
		assertEquals(225055, (int) DKK.valueInThisCurrency(300075, SEK));
		assertEquals(200050, (int) EUR.valueInThisCurrency(200050, EUR));
	}

}