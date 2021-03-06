package uk.co.ramyun.herblore.util;

import org.osbot.rs07.api.Bank.BankMode;
import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.utility.ConditionalSleep;

public class Banker {

	/**
	 * @author � Michael 1 Jan 2018
	 * @file Banker.java
	 */

	public Banker() {}

	/**
	 * Describes the status of a bank withdraw attempt.
	 */
	public enum WithdrawStatus {
		SUCCESS(false), INSUFFICIENT_AMOUNT(true), INSUFFICIENT_SPACE(false), ACTION_FAIL(false);
		private final boolean fatal;

		WithdrawStatus(boolean fatal) {
			this.fatal = fatal;
		}

		public boolean isFatal() {
			return fatal;
		}
	}

	/**
	 * 'Sets' the amount of an item in inventory
	 * 
	 * @param mp the MethodProvider instance
	 * @param itemName the item to withdraw
	 * @param total the desired amount of said item
	 * @param stackable whether the item is stackable
	 * @param noted whether to withdraw noted
	 * @param forceAmount whether the amount is forced or fewer of the item is
	 *            OK
	 * @throws InterruptedException
	 */
	public WithdrawStatus withdrawItem(MethodProvider mp, String itemName, int total, boolean stackable, boolean noted,
			boolean forceAmount) throws InterruptedException {
		if (mp.getBank().isOpen()) {
			int current = 0, emptySlots = mp.getInventory().getEmptySlots(), needed = 0,
					amountInBank = (int) mp.getBank().getAmount(itemName);
			ItemSleep itemSleep = new ItemSleep(mp, itemName, 4000),
					itemSleepGreater = new ItemSleep(mp, itemName, false, 4000);
			BankMode requiredMode = noted ? BankMode.WITHDRAW_NOTE : BankMode.WITHDRAW_ITEM;
			if (mp.getInventory().contains(itemName)) current = (int) mp.getInventory().getAmount(itemName);
			if (current == total) return WithdrawStatus.SUCCESS;
			needed = total - current;
			if (!mp.getBank().getWithdrawMode().equals(requiredMode)) mp.getBank().enableMode(requiredMode);
			if (amountInBank == 0 && needed > 0) return WithdrawStatus.INSUFFICIENT_AMOUNT;
			if (forceAmount) {
				if (amountInBank < needed) return WithdrawStatus.INSUFFICIENT_AMOUNT;
			} else if (amountInBank < needed) needed = amountInBank;
			if (stackable || noted) {
				/* TODO: Will not work if noted and inv full of un-noted item */
				if (emptySlots >= 1 || current > 0) {
					if (mp.getBank().withdraw(itemName, needed) && itemSleep.sleep()) return WithdrawStatus.SUCCESS;
				} else if (emptySlots == 0 && current == 0) return WithdrawStatus.INSUFFICIENT_SPACE;
			} else {
				if (emptySlots == 0) {
					return WithdrawStatus.INSUFFICIENT_SPACE;
				} else if (emptySlots == needed) {
					if (mp.getBank().withdrawAll(itemName) && itemSleep.sleep()) return WithdrawStatus.SUCCESS;
				} else if (emptySlots < needed) {
					return WithdrawStatus.INSUFFICIENT_SPACE;
				} else if (current > total) {
					if (mp.getBank().deposit(itemName, current - total) && itemSleepGreater.sleep())
						return WithdrawStatus.SUCCESS;
				} else if (current < total) {
					if (mp.getBank().withdraw(itemName, needed) && itemSleep.sleep()) return WithdrawStatus.SUCCESS;
				}
			}
		} else if (mp.getBank().open()) new BankSleep(mp, true, 4000).sleep();
		return WithdrawStatus.ACTION_FAIL;
	}

	/**
	 * For stackable items only
	 * 
	 * @param mp the MethodProvider instance
	 * @param minimumAmount the minimum item amount required
	 * @param itemName the item to withdraw
	 * @throws InterruptedException
	 */
	public WithdrawStatus withdrawAllButOne(MethodProvider mp, long minimumAmount, String itemName)
			throws InterruptedException {
		if (mp.getBank().isOpen()) {
			long currentAmount = mp.getInventory().contains(itemName) ? mp.getInventory().getAmount(itemName) : 0,
					minimumNeeded = minimumAmount - currentAmount;
			ItemSleep itemSleep = new ItemSleep(mp, itemName, 4000);
			if (currentAmount > minimumAmount) return WithdrawStatus.SUCCESS;
			else if (mp.getInventory().getEmptySlots() > 0 || currentAmount > 0) {
				if (mp.getBank().contains(itemName) && mp.getBank().getAmount(itemName) > minimumNeeded) {
					if (mp.getBank().withdrawAllButOne(itemName) && itemSleep.sleep()) return WithdrawStatus.SUCCESS;
				} else return WithdrawStatus.INSUFFICIENT_AMOUNT;
			} else return WithdrawStatus.INSUFFICIENT_SPACE;
		} else if (mp.getBank().open()) new BankSleep(mp, true, 4000).sleep();
		return WithdrawStatus.ACTION_FAIL;
	}

	public boolean sleepUntilInventoryEmpty(MethodProvider mp, int timeout) {
		return new ConditionalSleep(timeout) {
			@Override
			public boolean condition() throws InterruptedException {
				return mp.getInventory().isEmpty();
			}
		}.sleep();
	}

}
