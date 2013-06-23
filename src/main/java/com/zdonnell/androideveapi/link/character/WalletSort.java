package com.zdonnell.androideveapi.link.character;

import java.util.Comparator;

import com.zdonnell.androideveapi.shared.wallet.journal.ApiJournalEntry;
import com.zdonnell.androideveapi.shared.wallet.transactions.ApiWalletTransaction;

public class WalletSort {
	public static class Journal {
		public static class RefID implements Comparator<ApiJournalEntry> {
			public int compare(ApiJournalEntry lhs, ApiJournalEntry rhs) {			
				if (lhs.getRefID() < rhs.getRefID()) return -1;
				else return 1;
			}
		}
		
		public static class DateTime implements Comparator<ApiJournalEntry> {
			public int compare(ApiJournalEntry lhs, ApiJournalEntry rhs) {			
				// the wallet entry dateTime string is properly formatted such that
				// a simple alpha numeric sort will suffice
				return rhs.getDate().compareTo(lhs.getDate());
			}
		}
	}
	
	public static class Transactions {
		public static class ID implements Comparator<ApiWalletTransaction> {
			public int compare(ApiWalletTransaction lhs, ApiWalletTransaction rhs)  {			
				if (lhs.getTransactionID() < rhs.getTransactionID()) return -1;
				else return 1;
			}
		}
		
		public static class DateTime implements Comparator<ApiWalletTransaction> {
			public int compare(ApiWalletTransaction lhs, ApiWalletTransaction rhs) {			
				// the wallet entry dateTime string is properly formatted such that
				// a simple alpha numeric sort will suffice
				return rhs.getTransactionDateTime().compareTo(lhs.getTransactionDateTime());
			}
		}
	}
}
