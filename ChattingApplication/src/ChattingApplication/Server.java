package ChattingApplication;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Server implements ActionListener {
    JButton send;
    JTextField text;
    JPanel a1;
    static Box Vertical = Box.createVerticalBox();
    static JFrame f = new JFrame();
    static DataOutputStream dout;
    Server(){
        f.getContentPane().setBackground(Color.white);
        f.setLayout(null);

        JPanel p1 = new JPanel();
        p1.setBounds(0,0,450,70);
        p1.setBackground(new Color(7,94,84));
        p1.setLayout(null);
        f.add(p1);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(5,22,25,25);
        p1.add(back);

        back.addMouseListener(new MouseAdapter() {
            public  void mouseClicked(MouseEvent ae){
                System.exit(0);
            }
        });

        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/myy.png"));
        Image i5 = i4.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile = new JLabel(i6);
        profile.setBounds(40,10,50,50);
        p1.add(profile);

        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i8 = i7.getImage().getScaledInstance(35,30,Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel video = new JLabel(i9);
        video.setBounds(300,20,30,30);
        p1.add(video);

        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image i11 = i10.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel phone = new JLabel(i12);
        phone.setBounds(360,20,35,30);
        p1.add(phone);

        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image i14 = i13.getImage().getScaledInstance(10,25,Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel morevert = new JLabel(i15);
        morevert.setBounds(415,22,10,25);
        p1.add(morevert);

        JLabel name = new JLabel("Rahul");
        name.setBounds(110,16,100,18);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("STLiti", Font.BOLD,23));
        p1.add(name);

        JLabel status = new JLabel("Active Now");
        status.setBounds(110,40,100,13);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("STLiti", Font.BOLD,18));
        p1.add(status);


        a1 = new JPanel();
        a1.setBounds(5,75,440,525);
        f.add(a1);

        text = new JTextField();
        text.setBounds(5,605,310,40);
        text.setFont(new Font("STLiti",Font.PLAIN,16));
        f.add(text);

        send = new JButton("Send");
        send.setBounds(320,605,123,40);
        send.setBackground(new Color(7,94,84));
        send.setFont(new Font("STLiti",Font.PLAIN,16));
        send.setForeground(Color.WHITE);
        send.addActionListener(this);
        f.add(send);



        f.setBounds(100,13,450,650);
        f.setUndecorated(true);
        f.setVisible(true);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String out = text.getText();

        JPanel p2 = formatLablel(out);


        a1.setLayout(new BorderLayout());

        JPanel rgiht = new JPanel(new BorderLayout());
        rgiht.add(p2, BorderLayout.LINE_END);
        Vertical.add(rgiht);
        Vertical.add(Box.createVerticalStrut(15));

        a1.add(Vertical, BorderLayout.PAGE_START);

        try {
            dout.writeUTF(out);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        text.setText("");

        f.repaint();
        f.invalidate();
        f.validate();
    }
    public static  JPanel formatLablel(String out){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));

        JLabel output = new JLabel("<html><p style=\"width :120px\">"+out+"</p></html>");
        output.setFont(new Font("STLiti",Font.PLAIN,16));
        output.setBackground(new Color(37,211,102));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(12,12,12,50));
        panel.add(output);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));

        panel.add(time);

        return panel;
    }
    public static void main(String[] args) {
        new Server();
        try {
            ServerSocket skt = new ServerSocket(6001);

            while(true) {
                Socket s= skt.accept();
                DataInputStream din = new DataInputStream(s.getInputStream());
                dout = new DataOutputStream(s.getOutputStream());

                while (true){
                    String msg = din.readUTF();
                    JPanel panel = formatLablel(msg);

                    JPanel left = new JPanel(new BorderLayout());
                    left.add(panel, BorderLayout.LINE_START);
                    Vertical.add(left);
                    f.validate();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
