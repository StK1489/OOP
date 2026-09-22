package ru.nsu.vkuznetsov.task112.game;

import ru.nsu.vkuznetsov.task112.domain.Dealer;
import ru.nsu.vkuznetsov.task112.domain.Player;
import ru.nsu.vkuznetsov.task112.domain.RoundResult;

/**
 * Определяет победителя раунда по правилам блэкджека.
 */
public class RoundResolver {

    /**
     * Определяет результат раунда.
     *
     * @param player игрок
     * @param dealer дилер
     * @return результат раунда
     */
    public RoundResult resolve(Player player, Dealer dealer) {
        if (player.isBusted()) {
            return RoundResult.DEALER_WIN;
        }
        if (dealer.isBusted()) {
            return RoundResult.PLAYER_WIN;
        }
        if (player.getScore() > dealer.getScore()) {
            return RoundResult.PLAYER_WIN;
        }
        if (player.getScore() < dealer.getScore()) {
            return RoundResult.DEALER_WIN;
        }
        return RoundResult.DRAW;
    }
}