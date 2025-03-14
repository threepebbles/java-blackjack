package controller;

import domain.BlackjackManager;
import domain.card.Deck;
import domain.card.DeckGenerator;
import domain.player.Dealer;
import domain.player.Players;
import domain.player.User;
import domain.player.Users;
import java.util.ArrayList;
import java.util.List;
import view.InputView;
import view.OutputView;

public class BlackjackController {

    public void run() {
        BlackjackManager blackjackManager = createBlackjackManager();

        distributeInitialCards(blackjackManager);
        hitUntilAllStay(blackjackManager);
        printCardsAndSum(blackjackManager);
        printProfit(blackjackManager);
    }

    private void distributeInitialCards(BlackjackManager blackjackManager) {
        blackjackManager.distributeInitialCards();
        blackjackManager.openInitialCards();
        OutputView.printInitialCards(
                blackjackManager.getDealer(),
                blackjackManager.getUsers()
        );
    }

    private void hitUntilAllStay(BlackjackManager blackjackManager) {
        blackjackManager.hitUntilAllUsersStay(InputView::inputWantOneMoreCard, OutputView::printPlayerCards);

        if (blackjackManager.addCardToDealerIfLowSum()) {
            OutputView.printDealerHitIfLowSum();
        }
    }

    private void printCardsAndSum(BlackjackManager blackjackManager) {
        OutputView.printCardsAndSum(blackjackManager.getDealer(),
                blackjackManager.getUsers(),
                blackjackManager.computePlayerSum());
    }

    private void printProfit(BlackjackManager blackjackManager) {
        OutputView.printProfit(blackjackManager.computeDealerProfit(), blackjackManager.computeUsersProfit());
    }

    private BlackjackManager createBlackjackManager() {
        Users users = createUsers(InputView.inputUserNames());
        Dealer dealer = new Dealer();
        Deck deck = DeckGenerator.generateDeck();
        Players players = createPlayers(dealer, users);
        return new BlackjackManager(players, deck);
    }

    private Users createUsers(List<String> names) {
        List<User> users = new ArrayList<>();
        for (String name : names) {
            int bet = InputView.inputBet(name);
            users.add(new User(name, bet));
        }
        return new Users(users);
    }

    private Players createPlayers(Dealer dealer, Users users) {
        return new Players(dealer, users);
    }
}
