package domain.player;

import domain.Bet;

public class User extends Player {

    private Bet bet;

    public User(String name) {
        super(name);
        this.bet = new Bet(0);
    }

    public User(String name, int bet) {
        super(name);
        this.bet = new Bet(bet);
    }

    @Override
    public void openInitialCards() {
        openCards(2);
    }
}
