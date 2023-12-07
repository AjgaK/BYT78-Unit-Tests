package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BankTest {
	Currency SEK, DKK;
	Bank SweBank, Nordea, DanskeBank;

	@Before
	public void setUp() throws Exception {
		DKK = new Currency("DKK", 0.20);
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		Nordea = new Bank("Nordea", SEK);
		DanskeBank = new Bank("DanskeBank", DKK);
		SweBank.openAccount("Ulrika");
		SweBank.openAccount("Bob");
		Nordea.openAccount("Bob");
		DanskeBank.openAccount("Gertrud");
	}

	@Test
	public void testGetName() {
		assertEquals("SweBank", SweBank.getName());
		assertEquals("Nordea", Nordea.getName());
		assertEquals("DanskeBank", DanskeBank.getName());
	}

	@Test
	public void testGetCurrency() {
		assertEquals(SEK, SweBank.getCurrency());
		assertEquals(DKK, DanskeBank.getCurrency());
		assertEquals(SEK, Nordea.getCurrency());
	}

	@Test
	public void testOpenAccount() throws AccountExistsException, AccountDoesNotExistException {
		// failed
		SweBank.openAccount("123");
	}

	@Test(expected = AccountExistsException.class)
	public void testOpenExistingAccount() throws AccountExistsException {
		SweBank.openAccount("Ulrika");
	}

	@Test
	public void testDeposit() throws AccountDoesNotExistException {
		// Test if deposit() correctly deposits money into an account and updates the
		// balance.

		// failed
		Money money = new Money(1000, DKK);
		DanskeBank.deposit("Gertrud", money);
		assertEquals(1000, DanskeBank.getBalance("Gertrud").intValue());
	}

	@Test
	public void testWithdraw() throws AccountDoesNotExistException {
		// Test if withdraw() correctly withdraws money from an account and updates
		// the balance

		// failed
		SweBank.deposit("Ulrika", new Money(1000, SEK));
		SweBank.withdraw("Ulrika", new Money(500, SEK));
		assertEquals(500, SweBank.getBalance("Ulrika").intValue());
	}

	@Test
	public void testGetBalance() throws AccountDoesNotExistException {
		// Test if getBalance() correctly retrieves the balance of an account
		Nordea.deposit("Bob", new Money(1000, SEK));
		assertEquals(1000, Nordea.getBalance("Bob").intValue());
	}

	@Test
	public void testTransfer() throws AccountDoesNotExistException {
		// Test if transfer() correctly transfers money between accounts within the same
		// bank.

		// failed
		SweBank.deposit("Ulrika", new Money(1000, SEK));
		SweBank.transfer("Ulrika", "Bob", new Money(500, SEK));
		assertEquals(500, SweBank.getBalance("Ulrika").intValue());
		assertEquals(500, SweBank.getBalance("Bob").intValue());
	}

	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		// Test if addTimedPayment() correctly schedules and tick() executes timed
		// payments.
		SweBank.addTimedPayment("Ulrika", "payment1", 2, 0, new Money(500, SEK), Nordea, "Bob");
		SweBank.tick();
		assertEquals(500, Nordea.getBalance("Bob").intValue());
	}
}
