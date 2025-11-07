package dev.vvalio.gifter;

/**
 * Specifies which rules apply to the gift exchange.
 *
 * @param allowSameFamily  true if gifting is allowed inside one defined family
 * @param allowEachOther   true if two people can gift each other (larger circles are not considered)
 * @param blacklist        whether to consider the blacklist provided {@link ExchangeConfiguration}
 * @param enforceHardScore whether to fail if the problem cannot be solved without breaking hard constraints
 * @author vvalio
 */
public record ExchangeRules(boolean allowSameFamily, boolean allowEachOther, boolean blacklist,
                            boolean enforceHardScore) {

}
