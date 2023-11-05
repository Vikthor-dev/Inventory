import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.*;
import java.util.ArrayList;

public class Dash_out_form {
    private JPanel main_dash_out_form_panel;
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
    private JButton okButton;
    private JTextField trans_type_text_field;
    private JComboBox select_prod_out_cbox;
    private JLabel errorLabel;

    public JFrame dash_out_frame = new JFrame();
    public DefaultComboBoxModel defaultModel2 = new DefaultComboBoxModel();
    public ArrayList<Product> listaProizvoda_3 = new ArrayList<>();
    Product prod2 = new Product();

    IMS_Main main;

    public Dash_out_form() {

        cancel_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dash_out_frame.dispose();
            }
        });
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String host = "jdbc:mysql://localhost:4000/products?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
                    String uName = "root";
                    String uPass = "a230385";
                    Connection con = DriverManager.getConnection(host, uName, uPass);

                    int quantity_update = Integer.parseInt(quantity_text_field.getText());
                    int curr_stock = prod2.getStock();
                    int final_stock = curr_stock - quantity_update;
                    String prod_name = prod2.getName();

                    Statement stat = con.createStatement();

                    String trans_type = trans_type_text_field.getText();
                    int quantity = Integer.parseInt(quantity_text_field.getText());
                    double price = prod2.getPrice();
                    String date = date_text_field.getText();
                    if((curr_stock - quantity) >= 0){
                        stat.executeUpdate("update data set Stock ='"+final_stock+"' where Name = '"+prod_name+"' ");
                        stat.executeUpdate("insert into data_dash(Trans_type,Quantity,Name,Price,Date)" +
                                "values('"+trans_type+"','"+quantity+"','"+prod_name+"','"+price+"','"+date+"')");
                        stat.close();
                        dash_out_frame.dispose();
                        System.out.println("Successfully updated stock count and added a transaction!");
                        main.connect_to_data_dash();
                    }else{
                        errorLabel.setText("No enought product on stock, try with less");
                        errorLabel.setVisible(true);
                        quantity_panel.setBorder(BorderFactory.createLineBorder(Color.red));
                    }

                } catch (SQLException err) {
                    System.out.println(err.getMessage());
                }
            }
        });
        select_prod_out_cbox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                trans_type_text_field.setText("OUT");
                int prod_number = select_prod_out_cbox.getSelectedIndex();
                if (prod_number >= 0){
                    prod2 = listaProizvoda_3.get(prod_number);
                    curr_stock_text_field.setText(Integer.toString(prod2.getStock()));
                }
            }
        });
    }

    public void get_data_dash_out(){
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

                if(stock > 0){
                    listaProizvoda_3.add(new Product(name,price,desc,date,stock));
                }
            }
            select_prod_out_cbox.setModel(defaultModel2);
            for (Product p : listaProizvoda_3){

                defaultModel2.addElement(p.getName());

            }

        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

    public void dash_out_form(JFrame frame, IMS_Main main){
        this.main = main;
        frame.setContentPane(main_dash_out_form_panel);
        frame.setTitle("Dash Out Form");
        frame.setSize(400,400);
        frame.setVisible(true);
        errorLabel.setVisible(false);
        get_data_dash_out();
    }

    public static void main(String[] args) {

    }
}
