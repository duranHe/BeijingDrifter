package org.heyuning.ui;

import org.heyuning.player.Player;
import org.heyuning.product.BlackMarket;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Game {
    public static void main(String[] args) {
        BlackMarket blackMarket = new BlackMarket();
        Player player = new Player();
        UIManager ui = new UIManager(player, blackMarket);
        Game game = new Game();
    }
}
