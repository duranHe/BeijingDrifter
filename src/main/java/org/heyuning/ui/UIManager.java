package org.heyuning.ui;

import org.heyuning.player.Player;
import org.heyuning.product.BlackMarket;
import org.heyuning.product.Inventory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.heyuning.util.Constant.MAX_PRODUCT;

public class UIManager {
    public Player player;
    public BlackMarket blackMarket;

    public Box progressBox;

    public Box tradeBox;
    public Box blackMarketBox;
    public JTable blackMarketTable;
    public Box tradeActionBox;
    public Box inventoryBox;
    public JTable inventoryTable;

    public Box actionBox;
    public Box statusBox;
    public Box locationBox;

    public Box utilityBox;

    class SellButtonListener implements  ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO: enforce to select a row
            int selectRow = inventoryTable.getSelectedRow();
            String productToSell = (String)inventoryTable.getValueAt(selectRow, 0);
            Inventory inventory = player.inventories.get(productToSell);
            int maxSellAmount = inventory.amount;

            JFrame parent = new JFrame();
            // TODO:
            // 1. custom button text
            // 2. Check int value, negative value
            String input = JOptionPane.showInputDialog(
                    parent,
                    "买入数量",
                    String.valueOf(maxSellAmount));

            // confirm sell
            if(input != null) {
                int sellAmount = Integer.valueOf(input);

                // TODO: what if not available on black market
                int sellPrice = blackMarket.products.get(productToSell);

                System.out.println(String.format("sell: product: %s, price: %d, amount: %d", productToSell, sellPrice, sellAmount));
                boolean isSellingAll = player.sellProduct(productToSell, sellPrice, sellAmount);
                updateInventoryTable(isSellingAll, productToSell);
            }
        }

        private void updateInventoryTable(boolean isSellingAll, String productToSell) {
            DefaultTableModel model = (DefaultTableModel) (inventoryTable.getModel());
            Inventory inventory = player.inventories.get(productToSell);

            for(int i = 0; i < model.getRowCount(); i++) {
                String rowProduct = (String) model.getValueAt(i, 0);
                if(rowProduct.equals(productToSell)) {
                    if(isSellingAll) {
                        model.removeRow(i);
                    } else {
                        model.setValueAt(inventory.amount, i, 2);
                    }
                    break;
                }
            }
        }
    }

    class BuyButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO: enforce to select a row
            int selectRow = blackMarketTable.getSelectedRow();
            int price = (int)blackMarketTable.getValueAt(selectRow, 1);

            // TODO: enforce max buy amount
            int maxBuyAmount = Math.min(player.room - player.load, player.cash / price);
            JFrame parent = new JFrame();
            Object[] optionText = {"买了买了", "再考虑下"};

            // TODO:
            // 1. custom button text
            // 2. Check int value, negative value
            String input = JOptionPane.showInputDialog(
                    parent,
                    "买入数量",
                    String.valueOf(maxBuyAmount));

            // confirm buy
            if(input != null) {
                String productToBuy = (String)blackMarketTable.getValueAt(selectRow, 0);
                int buyAmount = Integer.valueOf(input);

                System.out.println(String.format("buy: product: %s, cost: %d, amount: %d", productToBuy, price, buyAmount));
                boolean isNewProduct = player.buyProduct(productToBuy, price, buyAmount);
                updateInventoryTable(isNewProduct, productToBuy);
            }
        }
    }

    private void updateInventoryTable(boolean isNewProduct, String product) {
        DefaultTableModel model = (DefaultTableModel) (inventoryTable.getModel());
        Inventory inventory = player.inventories.get(product);
        if(isNewProduct) {
            model.addRow(new Object[]{inventory.productName, inventory.cost, inventory.amount});
        } else {
            for(int i = 0; i < model.getRowCount(); i++) {
                String rowProduct = (String)model.getValueAt(i, 0);
                if(rowProduct.equals(product)) {
                    model.setValueAt(inventory.cost, i, 1);
                    model.setValueAt(inventory.amount, i, 2);
                    break;
                }
            }
        }
    }

    private Box getProgressBox() {
        Box progressBox = Box.createHorizontalBox();
        progressBox.add(new JLabel("北京浮生 第（1 / 40）天"));
        return progressBox;
    }

    private void initBlackMarketBox() {
        blackMarketBox = Box.createVerticalBox();
        blackMarketBox.add(new JLabel("地铁门口的黑市"));

        Object[][] tableData = {
                new Object[]{"盗版光盘", 15},
                new Object[]{"婴儿奶粉", 25},
                new Object[]{"水货手机", 25}
        };

        Object[] tableTitle = {"商品", "黑市价格"};

//        blackMarketTable = new JTable(tableData, tableTitle);
        blackMarketTable = new JTable(new DefaultTableModel(tableData, tableTitle));
        blackMarketTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        blackMarketTable.setPreferredScrollableViewportSize(
                new Dimension(
                        blackMarketTable.getPreferredSize().width,
                        blackMarketTable.getRowHeight() * MAX_PRODUCT));
        blackMarketBox.add(new JScrollPane(blackMarketTable));
    }

    private void initInventoryBox() {
        inventoryBox = Box.createVerticalBox();
        inventoryBox.add(new JLabel("您的出租屋内"));

        Object[][] tableData = {
//                new Object[]{"盗版光盘", 15, 10},
//                new Object[]{"婴儿奶粉", 25, 5}
        };

        Object[] tableTitle = {"商品", "买入价格", "数量"};
//        inventoryTable = new JTable(tableData, tableTitle);
        inventoryTable = new JTable(new DefaultTableModel(tableData, tableTitle));
        inventoryTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        inventoryTable.setPreferredScrollableViewportSize(
                new Dimension(
                        inventoryTable.getPreferredSize().width,
                        inventoryTable.getRowHeight() * MAX_PRODUCT));
        inventoryBox.add(new JScrollPane(inventoryTable));
    }

    private void initTradeActionBox() {
        tradeActionBox = Box.createVerticalBox();

        JButton buyButton = new JButton("买入");
        buyButton.addActionListener(new BuyButtonListener());

        JButton sellButton = new JButton("卖出");
        sellButton.addActionListener(new SellButtonListener());

        tradeActionBox.add(buyButton);
        tradeActionBox.add(sellButton);
    }

    private void initTradeBox() {
        tradeBox = Box.createHorizontalBox();

        initBlackMarketBox();
        initTradeActionBox();
        initInventoryBox();

        tradeBox.add(blackMarketBox);
        tradeBox.add(tradeActionBox);
        tradeBox.add(inventoryBox);
    }

    private void initStatusBox() {
        statusBox = Box.createVerticalBox();

        JLabel cash = new JLabel("2000", JLabel.CENTER);

        cash.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.RED));


        JLabel saving = new JLabel("0");
        JLabel debt = new JLabel("5000");
        JLabel health = new JLabel("100");
        statusBox.add(cash);
        statusBox.add(saving);
        statusBox.add(debt);
        statusBox.add(health);
    }

    private void initActionBox() {
        actionBox = Box.createHorizontalBox();
        initStatusBox();
        locationBox = Box.createHorizontalBox();

        locationBox.add(new JButton("location"));
        actionBox.add(statusBox);
        actionBox.add(locationBox);

        actionBox.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.BLACK));
    }

    // TODO: 可以买暂住证
    private Box getUtilityBox() {
        Box utilityBox = Box.createHorizontalBox();
        utilityBox.add(new JButton("utility"));
        return utilityBox;
    }

    public UIManager(Player player, BlackMarket blackMarket) {
        this.player = player;
        this.blackMarket = blackMarket;

        JFrame gameFrame = new JFrame("北京浮生记");

        progressBox = getProgressBox();
        initTradeBox();
        initActionBox();
        utilityBox = getUtilityBox();

        progressBox.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.BLACK));
        tradeBox.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.BLUE));
        actionBox.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.GREEN));
        utilityBox.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.RED));
        gameFrame.getRootPane().setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.ORANGE));

        gameFrame.add(progressBox);
        gameFrame.add(tradeBox);
        gameFrame.add(actionBox);
        gameFrame.add(utilityBox);

        gameFrame.setVisible(true);
        gameFrame.setLayout(new BoxLayout(gameFrame.getContentPane(), BoxLayout.Y_AXIS));
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        actionBox.setSize(new Dimension(tradeBox.getWidth(), 200));

        gameFrame.pack();



        System.out.println("Root width: " + gameFrame.getRootPane().getWidth());
        System.out.println("TradeBox: " + tradeBox.getWidth());
        System.out.println("actionBox: " + actionBox.getWidth());
    }
}
