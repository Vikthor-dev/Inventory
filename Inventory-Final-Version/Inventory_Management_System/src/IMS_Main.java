import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.sql.*;

public class IMS_Main extends JFrame {

    private JPanel main_panel;
    private JLabel text_title;
    private JButton btn_dash;
    private JButton btn_prod;
    private JButton btn_stock;
    private JButton btn_trans;
    private JPanel panel_up;
    private JPanel panel_left;
    private JPanel panel_right;
    private JPanel panel_dash;
    private JPanel panel_prod;
    private JPanel panel_lstock;
    private JPanel panel_trans;
    private JLabel dash_label;
    private JPanel dash_panel_top;
    private JPanel prod_panel_top;
    private JLabel prod_label;
    private JPanel stock_panel_top;
    private JPanel trans_panel_top;
    private JLabel trans_label;
    private JLabel lstock_label;
    private JButton add_prod_btn;
    private JPanel prod_panel_bottom;
    private JPanel panel_dash_bottom;
    private JButton dash_btn_in;
    private JButton dash_btn_out;
    private JPanel dash_panel_middle;
    private JPanel prod_panel_mid;
    private JList prod_panel_list;
    private JList dash_panel_list;
    private JPanel stock_panel_mid;
    private JList stock_panel_list;
    private JPanel trans_panel_mid;
    private JList trans_panel_list;
    private JTable dashT;
    private JTable prodT;
    private JTable transT;
    private JTable stockT;
    private JPanel stock_panel_bottom;

    public DefaultTableModel prodData = new DefaultTableModel(new Object[]{"Product", "Price", "Description", "Date", "Stock"}, 0);
    public ArrayList<Product> listaProizvoda = new ArrayList<Product>();
    public DefaultTableModel dashData = new DefaultTableModel(new Object[]{"Type","Product", "Price",  "Date", "Quantity"}, 0);
    public ArrayList<ProductDash> listaProizvoda_dash = new ArrayList<>();
    public DefaultTableModel stockData = new DefaultTableModel(new Object[]{"Type","Product", "Price",  "Date", "Quantity"}, 0);
    public ArrayList<Product> listaProizvoda_stock = new ArrayList<Product>();
    public DefaultTableModel transData = new DefaultTableModel(new Object[]{"Type","Product", "Price",  "Date", "Quantity"}, 0);
    public ArrayList<ProductDash> listaProizvoda_trans = new ArrayList<>();

    public  void refresh(){
        try {
            String host = "jdbc:mysql://localhost:4000/products?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
            String uName = "root";
            String uPass = "a230385";
            Connection con = DriverManager.getConnection(host, uName, uPass);

            Statement stat = con.createStatement();
            String sql = "select * from data";
            ResultSet rs = stat.executeQuery(sql);

            while ( rs.next()) {
                int id_col = rs.getInt("ID");
                String name = rs.getString("Name");
                double price = rs.getDouble("Price");
                String date = rs.getString("Date");
                String desc = rs.getString("Description");
                int stock = rs.getInt("Stock");

                listaProizvoda.add(new Product(name,price,desc,date,stock));
            }
            prodT.setModel(prodData);
            for (Product p : listaProizvoda){
                prodData.addRow(new Object[]{p.getName(), p.getPrice(), p.getDescription(), p.getDate(), p.getStock()});
            }

            for (int i = 0;i<listaProizvoda.size();i++){
                System.out.println(listaProizvoda.get(i));
            }


        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

    public IMS_Main(){
        super("Inventory Management System");
        this.setContentPane(this.main_panel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        IMS_Main self = this;
        prodT.setPreferredScrollableViewportSize(new Dimension(700,150));
        prodT.setFillsViewportHeight(true);

        dashT.setPreferredScrollableViewportSize(new Dimension(700,150));
        dashT.setFillsViewportHeight(true);

        transT.setPreferredScrollableViewportSize(new Dimension(700,150));
        transT.setFillsViewportHeight(true);

        stockT.setPreferredScrollableViewportSize(new Dimension(700,150));
        stockT.setFillsViewportHeight(true);

        btn_dash.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listaProizvoda_dash.removeAll(listaProizvoda_dash);
                int rowCount = dashData.getRowCount();
                for(int i = rowCount - 1; i >= 0; i--){
                    dashData.removeRow(i);
                }
                connect_to_data_dash();
                panel_right.removeAll();
                panel_right.add(panel_dash);
                panel_right.repaint();
                panel_right.revalidate();
            }
        });

        btn_prod.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listaProizvoda.removeAll(listaProizvoda);
                int rowCount = prodData.getRowCount();
                for(int i = rowCount - 1; i >= 0; i--){
                    prodData.removeRow(i);
                }
                refresh();
                panel_right.removeAll();
                panel_right.add(panel_prod);
                panel_right.repaint();
                panel_right.revalidate();
            }
        });

        btn_stock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listaProizvoda_stock.removeAll(listaProizvoda_stock);
                int rowCount = stockData.getRowCount();
                for(int i = rowCount - 1; i >= 0; i--){
                    stockData.removeRow(i);
                }
                connect_to_low_stock();
                panel_right.removeAll();
                panel_right.add(panel_lstock);
                panel_right.repaint();
                panel_right.revalidate();
            }
        });

        btn_trans.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listaProizvoda_trans.removeAll(listaProizvoda_trans);
                int rowCount = transData.getRowCount();
                for(int i = rowCount - 1; i >= 0; i--){
                    transData.removeRow(i);
                }
                connect_to_all_trans();
                panel_right.removeAll();
                panel_right.add(panel_trans);
                panel_right.repaint();
                panel_right.revalidate();
            }
        });
        add_prod_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Prod_form prod_form = new Prod_form();
                prod_form.open_prod_form(prod_form.frame_prod, self);
            }
        });
        dash_btn_in.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Dash_in_form dash_in_form = new Dash_in_form();
                dash_in_form.dash_in_form(dash_in_form.dash_in_frame, self);
            }
        });
        dash_btn_out.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Dash_out_form dash_out_form = new Dash_out_form();
                dash_out_form.dash_out_form(dash_out_form.dash_out_frame, self);
            }
        });
    }

    public void connect_to_data(){
        try {

            String host = "jdbc:mysql://localhost:4000/products?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
            String uName = "root";
            String uPass = "a230385";
            Connection con = DriverManager.getConnection(host, uName, uPass);

            listaProizvoda.clear();
            prodData.setRowCount(0);

            Statement stat = con.createStatement();
            String sql = "select * from data";
            ResultSet rs = stat.executeQuery(sql);

            while ( rs.next()) {
                int id_col = rs.getInt("ID");
                String name = rs.getString("Name");
                double price = rs.getDouble("Price");
                String date = rs.getString("Date");
                String desc = rs.getString("Description");
                int stock = rs.getInt("Stock");

                listaProizvoda.add(new Product(name,price,desc,date,stock));
            }
            prodT.setModel(prodData);
            for (Product p : listaProizvoda){
                prodData.addRow(new Object[]{p.getName(), p.getPrice(), p.getDescription(), p.getDate(), p.getStock()});
            }


        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

    public void connect_to_data_dash(){
        try {

            String host = "jdbc:mysql://localhost:4000/products?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
            String uName = "root";
            String uPass = "a230385";
            Connection con = DriverManager.getConnection(host, uName, uPass);
            listaProizvoda_dash.clear();
            dashData.setRowCount(0);
            Statement stat = con.createStatement();
            String sql = "select * from( SELECT * FROM data_dash ORDER BY ID DESC LIMIT 10) sub ";
            ResultSet rs = stat.executeQuery(sql);

            while ( rs.next()) {
                int id_col = rs.getInt("ID");
                String trans_type = rs.getString("Trans_type");
                int quantity = rs.getInt("Quantity");
                String name  = rs.getString("Name");
                double price = rs.getDouble("Price");
                String date = rs.getString("Date");

                listaProizvoda_dash.add(new ProductDash(trans_type,quantity,name,price,date));
            }
            dashT.setModel(dashData);
            for (ProductDash p : listaProizvoda_dash){
                dashData.addRow(new Object[]{p.getTrans_type(), p.getName(), p.getPrice()*p.getQuantity(), p.getDate(), p.getQuantity()});
            }
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

    public void connect_to_low_stock(){
        try {

            String host = "jdbc:mysql://localhost:4000/products?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
            String uName = "root";
            String uPass = "a230385";
            Connection con = DriverManager.getConnection(host, uName, uPass);

            listaProizvoda_stock.clear();
            stockData.setRowCount(0);

            Statement stat = con.createStatement();
            String sql = "select * from data where Stock<5";
            ResultSet rs = stat.executeQuery(sql);

            while ( rs.next()) {
                int id_col = rs.getInt("ID");
                String name = rs.getString("Name");
                double price = rs.getDouble("Price");
                String date = rs.getString("Date");
                String desc = rs.getString("Description");
                int stock = rs.getInt("Stock");

                listaProizvoda_stock.add(new Product(name,price,desc,date,stock));
            }
            stockT.setModel(stockData);
            for (Product p : listaProizvoda_stock){
                stockData.addRow(new Object[]{p.getName(), p.getPrice(), p.getDescription(), p.getDate(), p.getStock()});
            }

            for (int i = 0;i<listaProizvoda_stock.size();i++){
                System.out.println(listaProizvoda_stock.get(i));
            }

        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

    public void connect_to_all_trans(){
        try {

            String host = "jdbc:mysql://localhost:4000/products?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
            String uName = "root";
            String uPass = "a230385";
            Connection con = DriverManager.getConnection(host, uName, uPass);

            listaProizvoda_trans.clear();
            transData.setRowCount(0);

            Statement stat = con.createStatement();
            String sql = "select * from data_dash";
            ResultSet rs = stat.executeQuery(sql);

            while ( rs.next()) {
                int id_col = rs.getInt("ID");
                String trans_type = rs.getString("Trans_type");
                int quantity = rs.getInt("Quantity");
                String name  = rs.getString("Name");
                double price = rs.getDouble("Price");
                String date = rs.getString("Date");

                listaProizvoda_trans.add(new ProductDash(trans_type,quantity,name,price,date));
            }
            transT.setModel(transData);
            for (ProductDash p : listaProizvoda_trans){
                transData.addRow(new Object[]{p.getTrans_type(), p.getName(), p.getPrice(), p.getDate(), p.getQuantity()});
            }

            for (int i = 0;i<listaProizvoda_trans.size();i++){
                System.out.println(listaProizvoda_trans.get(i));
            }

        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

    public static void main(String[] args) {
        IMS_Main main_screen = new IMS_Main();
        main_screen.setVisible(true);
        main_screen.connect_to_data();
        main_screen.connect_to_data_dash();
        main_screen.connect_to_low_stock();
        main_screen.connect_to_all_trans();
    }
}
