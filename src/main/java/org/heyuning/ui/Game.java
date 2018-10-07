package org.heyuning.ui;

import org.heyuning.player.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Game {
    private static final int MAX_PRODUCT = 8;
    private Player player;

    class BuyButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int maxBuyAmount = 100;
            JFrame parent = new JFrame();
            Object[] optionText = {"买了买了", "再考虑下"};

            // TODO:
            // 1. custom button text
            // 2. Check int value
            String amount = JOptionPane.showInputDialog(
                    parent,
                    "买入数量",
                    String.valueOf(maxBuyAmount));
        }
    }

    private Box getProgressBox() {
        Box progressBox = Box.createHorizontalBox();
        progressBox.add(new JLabel("北京浮生 第（1 / 40）天"));
        return progressBox;
    }

    private Box getBlackMarketBox() {
        Box blackMarketBox = Box.createVerticalBox();
        blackMarketBox.add(new JLabel("地铁门口的黑市"));

        Object[][] tableData = {
                new Object[]{"盗版光盘", 15},
                new Object[]{"婴儿奶粉", 25}
        };

        Object[] tableTitle = {"商品", "黑市价格"};

        JTable blackMarket = new JTable(tableData, tableTitle);
        blackMarket.setPreferredScrollableViewportSize(new Dimension(blackMarket.getPreferredSize().width, blackMarket.getRowHeight() * MAX_PRODUCT));
        blackMarketBox.add(new JScrollPane(blackMarket));
        return blackMarketBox;
    }

    private Box getInventoryBox() {
        Box inventoryBox = Box.createVerticalBox();
        inventoryBox.add(new JLabel("您的出租屋内"));

        Object[][] tableData = {
                new Object[]{"盗版光盘", 15, 10},
                new Object[]{"婴儿奶粉", 25, 5}
        };

        Object[] tableTitle = {"商品", "买入价格", "数量"};
        JTable inventory = new JTable(tableData, tableTitle);
        inventory.setPreferredScrollableViewportSize(new Dimension(inventory.getPreferredSize().width, inventory.getRowHeight() * MAX_PRODUCT));
        inventoryBox.add(new JScrollPane(inventory));
        return inventoryBox;
    }

    private Box getTradeActionBox() {
        Box tradeActionBox = Box.createVerticalBox();

        JButton buyButton = new JButton("买入");
        buyButton.addActionListener(new BuyButtonListener());

        JButton sellButton = new JButton("卖出");

        tradeActionBox.add(buyButton);
        tradeActionBox.add(sellButton);
        return tradeActionBox;
    }

    private Box getTradeBox() {
        Box tradeBox = Box.createHorizontalBox();

        Box blackMarketBox = getBlackMarketBox();
        Box tradeActionBox = getTradeActionBox();
        Box inventoryBox = getInventoryBox();

        tradeBox.add(blackMarketBox);
        tradeBox.add(tradeActionBox);
        tradeBox.add(inventoryBox);

        return tradeBox;
    }

    private Box getActionBox() {

        Box actionBox = Box.createHorizontalBox();
        Box statusBox = Box.createVerticalBox();
        Box locationBox = Box.createHorizontalBox();

        statusBox.add(new JButton("status"));
        locationBox.add(new JButton("location"));
        actionBox.add(statusBox);
        actionBox.add(locationBox);

        return actionBox;
    }

    // TODO: 可以买暂住证
    private Box getUtilityBox() {
        Box utilityBox = Box.createHorizontalBox();
        utilityBox.add(new JButton("utility"));
        return utilityBox;
    }

    public void init() {
        JFrame gameFrame = new JFrame("北京浮生记");

        Box progressBox = getProgressBox();
        Box tradeBox = getTradeBox();
        Box actionBox = getActionBox();
        Box utilityBox = getUtilityBox();

        gameFrame.add(progressBox);
        gameFrame.add(tradeBox);
        gameFrame.add(actionBox);
        gameFrame.add(utilityBox);

        gameFrame.setVisible(true);
        gameFrame.setLayout(new BoxLayout(gameFrame.getContentPane(), BoxLayout.Y_AXIS));
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.pack();
    }

    public Game() {
        player = new Player();
    }

    public static void main(String[] args) {
        new Game().init();
    }
}
