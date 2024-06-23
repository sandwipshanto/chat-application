import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.net.*;

public class client implements ActionListener {

    JTextField text;
    static JPanel msf;
    static Box vertical = Box.createVerticalBox();

    static JFrame f = new JFrame();

    static DataOutputStream dout;

    client(){

        f.setLayout(null);

        JPanel p1 = new JPanel();
        p1.setBackground(new Color(7,94,84));
        p1.setBounds(0,0,450,70);
        p1.setLayout(null);
        f.add(p1);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(25,25, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(5,20,25,25);
        p1.add(back);

        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent ae) {
                f.setVisible(false);
            }
        });

        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image i5 = i4.getImage().getScaledInstance(35,30, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel more = new JLabel(i6);
        more.setBounds(410,20,10,25);
        p1.add(more);

        JLabel name = new JLabel("Kanto");
        name.setBounds(50,15,100,18);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        p1.add(name);

        JLabel status = new JLabel("Active");
        status.setBounds(50,40,100,18);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        p1.add(status);

        msf = new JPanel();
        msf.setBounds(5,75,425,540);
        f.add(msf);

        text = new JTextField();
        text.setBounds(5,615,340,40);
        text.setFont(new Font("SAN_SERIF", Font.PLAIN, 12));
        f.add(text);

        JButton send = new JButton("send");
        send.setBounds(345,615,80,40);
        send.setBackground(new Color(7,94,84));
        send.setForeground(Color.black);
        send.setFont(new Font("SAN_SERIF", Font.PLAIN, 12));
        send.addActionListener(this);
        f.add(send);


        f.setSize(450,700);
        f.setLocation(200,50);
        f.getContentPane().setBackground(Color.WHITE);



        f.setVisible(true);
    }

    public void actionPerformed(ActionEvent ae){
        try {
            String out = text.getText();


            JPanel p2 = formatLabel(out);

            msf.setLayout(new BorderLayout());

            JPanel right = new JPanel(new BorderLayout());
            right.add(p2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));

            msf.add(vertical, BorderLayout.PAGE_START);

            dout.writeUTF(out);

            text.setText("");
            f.repaint();
            f.invalidate();
            f.validate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static  JPanel formatLabel(String out) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));

        JLabel output = new JLabel(out);
        output.setFont(new Font("Tahoma", Font.PLAIN, 16));
        output.setBackground(new Color(37,211,102));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15,15,15,50));

        panel.add(output);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));

        panel.add(time);


        return panel;
    }
    public static void main(String[] args) {

        new client();

        try {
            Socket s = new Socket("127.0.0.1",6001);
            DataInputStream din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());
            //while(true) {
                //Socket s = skt.accept();


                while(true) {
                    msf.setLayout(new BorderLayout());
                    String msg = din.readUTF();
                    JPanel panel = formatLabel(msg);

                    JPanel left = new JPanel(new BorderLayout());
                    left.add(panel, BorderLayout.LINE_START);
                    vertical.add(left);

                    vertical.add(Box.createVerticalStrut(15));
                    msf.add(vertical, BorderLayout.PAGE_START);
                    f.validate();
                }
            }
         catch (Exception e) {
            e.printStackTrace();
        }
    }
}