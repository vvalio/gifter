package dev.vvalio.gifter;

/**
 * Specifies which rules apply to the gift exchange.
 *
 * @param allowSameFamily true if gifting is allowed inside one defined family
 * @param allowEachOther  true if two people can gift each other (larger circles are not considered)
 * @author vvalio
 */
public record ExchangeRules(boolean allowSameFamily, boolean allowEachOther) {

}
