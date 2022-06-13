import javax.swing.*;

public class FormNew extends JFrame{
    private JPanel mainPanel;
    public FormNew(){
        super("Graf");
        setSize(220,220);

        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(mainPanel);
    }
    public static void main(String[] args){
        JFrame newFrame=new FormNew();
        newFrame.setVisible(true);
    }
}
