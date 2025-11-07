# Gifter
A simple Java 21+ library to assign christmas/other gifts, secret santa style.

Use like so:
```java
final var rules = new ExchangeRules(
        /* Allow people with the same family ID to gift each other */
        false,
        /* Allow people to gift such that A gifts to B and B to A. */
        false,
        /* Consider the blacklist provided. */
        true,
        /* Fail if any of the previous constraints cannot be satisfied. */
        true
);

final var participants = List.of(
        /*              <name>         <id>  <family id> */
        new Participant("Mary Turner", "MT", "Turner"),
        new Participant("Johnny Turner", "JT", "Turner"),
        new Participant("Elle Turner", "ET", "Turner"),

        new Participant("Rick Johnson", "RJ", "Johnson"),
        new Participant("John Johnson", "JJ", "Johnson"),
        new Participant("Ashley Johnson", "AJ", "Johnson")
);

final var blacklist = new HashMap<String, Set<String>>() {
    {
        // we don't want Mary to gift Rick
        put("MT", "RJ");
    }
};

final var gifter = new Gifter(rules, participants, blacklist);
System.out.println(gifter.solve()); // map containing pairs, gifter -> receiver
System.out.print(gifter.getResultScore()); // hard/medium/soft score, 0/0/0 is optimal
```