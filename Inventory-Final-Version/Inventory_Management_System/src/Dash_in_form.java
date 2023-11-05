import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.*;
import java.util.ArrayList;

public class Dash_in_form extends JFrame {
    private JPanel main_dash_in_form_panel;
    private JPanel date_panel;
    private JLabel date_label;
    private JTextField date_text_field;
    private JPanel select_prod_panel;
    private JLabel select_prod_label;
    private JPanel trans_type_panel;
    private JLabel trans_type_label;
    private JPanel curr_stock_panel;
    private JLabel curr_stock_label;
    private JTextField curr_stock_text_field;
    private JPanel quantity_panel;
    private JLabel quantity_label;
    private JTextField quantity_text_field;
    private JPanel btn_panel;
    private JButton cancel_btn;
    private JButton ok_btn;
    private JTextField trans_type_text_field;
    private JComboBox select_prod_in_cbox;

    public JFrame dash_in_frame = new JFrame();
    public DefaultComboBoxModel defaultModel = new DefaultComboBoxModel();
    public ArrayList<Product> listaProizvoda_2 = new ArrayList<>();
    Product prod = new Product();
    IMS_Main main;
    public Dash_in_form() {

        select_prod_in_cbox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                trans_type_text_field.setText("IN");
                int product_number = select_prod_in_cbox.getSelectedIndex();
                if (product_number >=0){
                    prod = listaProizvoda_2.get(product_number);
                    curr_stock_text_field.setText(Integer.toString(prod.getStock()));
                }
            }
        });
        cancel_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dash_in_frame.dispose();
            }
        });
        ok_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String host = "jdbc:mysql://localhost:4000/products?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
                    String uName = "root";
                    String uPass = "a230385";
                    Connection con = DriverManager.getConnection(host, uName, uPass);

                    int quantity_update = Integer.parseInt(quantity_text_field.getText());
                    int curr_stock = prod.getStock();
                    int final_stock = quantity_update + curr_stock;
                    String prod_name = prod.getName();

                    Statement stat = con.createStatement();
                    stat.executeUpdate("update data set Stock ='"+final_stock+"' where Name = '"+prod_name+"' ");

                    String trans_type = trans_type_text_field.getText();
                    int quantity = Integer.parseInt(quantity_text_field.getText());
                    double price = prod.getPrice();
                    String date = date_text_field.getText();

                    stat.executeUpdate("insert into data_dash(Trans_type,Quantity,Name,Price,Date)" +
                            "values('"+trans_type+"','"+quantity+"','"+prod_name+"','"+price+"','"+date+"')");
                    stat.close();

                    //ims_main.refresh();
                    dash_in_frame.dispose();
                    System.out.println("Successfully updated stock count and added a transaction!");
                    main.connect_to_data_dash();
                } catch (SQLException err) {
                    System.out.println(err.getMessage());
                }
            }
        });
    }

    public void get_data_dash_in(){
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

                listaProizvoda_2.add(new Product(name,price,desc,date,stock));
            }
            select_prod_in_cbox.setModel(defaultModel);
            for (Product p : listaProizvoda_2){
                defaultModel.addElement(p.getName());
            }
            main.connect_to_data_dash();
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

    public void dash_in_form(JFrame frame, IMS_Main main){
        this.main = main;
        frame.setContentPane(main_dash_in_form_panel);
        frame.setTitle("Dash In Form");
        frame.setSize(400,400);
        frame.setVisible(true);

        get_data_dash_in();
        //select_prod_in_cbox.setModel(defaultModelIn);
        //defaultModelIn.addElement();
    }

    public static void main(String[] args) {

    }
}
