package b_Money;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AccountTest {
	Currency SEK, DKK;
	Bank Nordea;
	Bank DanskeBank;
	Bank SweBank;
	Account testAccount;

	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		SweBank.openAccount("Alice");
		testAccount = new Account("Hans", SEK);
		testAccount.deposit(new Money(10000000, SEK));

		SweBank.deposit("Alice", new Money(1000000, SEK));
	}

	@Test
	public void testAddRemoveTimedPayment() {
		// Test if adding and removing timed payments work correctly
		testAccount.addTimedPayment("payment1", 10, 5, new Money(1000, SEK), SweBank, "Alice");
		assertTrue(testAccount.timedPaymentExists("payment1"));
		testAccount.removeTimedPayment("payment1");
		assertFalse(testAccount.timedPaymentExists("payment1"));
	}

	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		// Test if timed payments are executed correctly after the specified number of
		// ticks
		testAccount.addTimedPayment("payment1", 1, 1, new Money(500, SEK), SweBank, "Alice");
		testAccount.tick();
		assertEquals(10000000 - 500, testAccount.getBalance().getAmount().intValue());
	}

	@Test
	public void testAddWithdraw() {
		// Test if deposit and withdraw functions update the account balance correctly
		testAccount.withdraw(new Money(1000, SEK));
		assertEquals(10000000 - 1000, testAccount.getBalance().getAmount().intValue());
		testAccount.deposit(new Money(500, SEK));
		assertEquals(10000000 - 500, testAccount.getBalance().getAmount().intValue());
	}

	@Test
	public void testGetBalance() {
		// Test if getBalance() correctly returns the current balance of the account
		assertEquals(10000000, testAccount.getBalance().getAmount().intValue());
	}
}
