import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Prod_form extends JFrame {
    private JPanel main_prod_form_panel;
    private JPanel date_panel;
    private JLabel date_label;
    private JTextField date_text_field;
    private JPanel prod_name_panel;
    private JLabel prod_name_label;
    private JTextField prod_name_text_field;
    private JPanel price_panel;
    private JLabel price_label;
    private JTextField price_text_field;
    private JPanel description_panel;
    private JLabel desc_label;
    private JTextArea desc_text_area;
    private JPanel btn_panel;
    private JButton cancel_btn;
    private JButton ok_btn;


    public JFrame frame_prod = new JFrame();

    IMS_Main main;
    public Prod_form() {

        ok_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String host = "jdbc:mysql://localhost:4000/products?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
                    String uName = "root";
                    String uPass = "a230385";
                    Connection con = DriverManager.getConnection(host, uName, uPass);

                    String name = prod_name_text_field.getText();
                    double price = Double.parseDouble(price_text_field.getText());
                    String date = date_text_field.getText();
                    String desc = desc_text_area.getText();
                    int stock_default = 0;

                    Statement stat = con.createStatement();
                    stat.executeUpdate("insert into data(Name,Price,Description,Date,Stock)" +
                            "values('"+name+"','"+price+"','"+desc+"','"+date+"','"+stock_default+"')");
                    stat.close();

                    IMS_Main ims_main = new IMS_Main();
                    //ims_main.refresh();
                    frame_prod.dispose();
                    System.out.println("Successfully added to database!");

                    main.connect_to_data();
                } catch (SQLException err) {
                    System.out.println(err.getMessage());
                }
            }
        });

        cancel_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame_prod.dispose();
            }
        });
    }

    public void open_prod_form(JFrame frame, IMS_Main main){
        this.main = main;
        frame.setContentPane(main_prod_form_panel);
        frame.setTitle("Add Product Form");
        frame.setSize(400,400);
        frame.setVisible(true);
    }

    public static void main(String[] args) {

    }
}
