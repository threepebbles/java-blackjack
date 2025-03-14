package domain.player;

import domain.bet.Bet;

public class User extends Player {

    private final Bet bet;

    public User(String name) {
        super(name);
        this.bet = Bet.defaultBet();
    }

    public User(String name, int bet) {
        super(name);
        this.bet = new Bet(bet);
    }

    @Override
    public void openInitialCards() {
        openCards(2);
    }

    public Bet getBet() {
        return bet;
    }
}
