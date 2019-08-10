package net.spicapvp.core.chat.filter.impl;

import net.spicapvp.core.SpicaCore;
import net.spicapvp.core.chat.filter.ChatFilter;

public class ContainsFilter extends ChatFilter {

	private final String phrase;

	public ContainsFilter(SpicaCore spicaCore, String phrase) {
		this(spicaCore, phrase, null);
	}

	public ContainsFilter(SpicaCore spicaCore, String phrase, String command) {
		super(spicaCore, command);
		this.phrase = phrase;
	}

	@Override
	public boolean isFiltered(String message, String[] words) {
		for (String word : words) {
			if (word.contains(this.phrase)) {
				return true;
			}
		}

		return false;
	}

}
