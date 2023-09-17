import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class server  implements ActionListener {
    JTextField text1;
    JPanel a1;
    static Box vertical=Box.createVerticalBox();
    static JFrame f =new JFrame();
    static DataOutputStream dout;
    server()
    {
        f.setLayout(null);
        JPanel p1 =new JPanel();
        p1.setBackground(new Color(7,94,84));
        p1.setBounds(0,0,450,70);
        p1.setLayout(null);
        f.add(p1);

//        to set image (back arrow)
        ImageIcon i1=new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2=i1.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
        ImageIcon i3= new ImageIcon(i2);
        JLabel back=new JLabel(i3);
        back.setBounds(5,20,25,25);
        p1.add(back);

        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });

//setting profile picture
        ImageIcon i4=new ImageIcon(ClassLoader.getSystemResource("icons/1.png"));
        Image i5=i4.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT);
        ImageIcon i6= new ImageIcon(i5);
        JLabel profile=new JLabel(i6);
        profile.setBounds(40,10,50,50);
        p1.add(profile);

        ImageIcon i7=new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i8=i7.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
        ImageIcon i9= new ImageIcon(i8);
        JLabel video=new JLabel(i9);
        video.setBounds(300,20,30,30);
        p1.add(video);

        ImageIcon i10=new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image i11=i10.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
        ImageIcon i12= new ImageIcon(i11);
        JLabel phone=new JLabel(i12);
        phone.setBounds(350,20,30,30);
        p1.add(phone);

        ImageIcon i13=new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image i14=i13.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
        ImageIcon i15= new ImageIcon(i14);
        JLabel morevert=new JLabel(i15);
        morevert.setBounds(400,20,10,30);
        p1.add(morevert);

//adding name
         JLabel name=new JLabel("shivam");
         name.setBounds(110,15,100,14 );
         name.setForeground(Color.WHITE);
         name.setFont(new Font("SAN_SERIF",Font.BOLD,18));
         p1.add(name);

//Adding status
        JLabel staus=new JLabel("code with coffee");
        staus.setBounds(110,35,100,15);
        staus.setForeground(Color.WHITE);
        staus.setFont(new Font("SAN_SERIF",Font.BOLD,10));
        p1.add(staus);

a1=new JPanel();
a1.setBounds(5,75,440,570);
f.add(a1);

//text box
 text1=new JTextField();
text1.setBounds(5,655,310,40);
text1.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
f.add(text1);

//send button
JButton send=new JButton("send");
send.setBounds(320,655,123,40);
send.setBackground(new Color(7,94,84));
send.setForeground(Color.WHITE);
send.addActionListener(this);
send.setFont(new Font("SAN_SERIF", Font.PLAIN,16));
f.add(send);

//sending chat to chat area





        f.setSize(450,700);
        f.setLocation(200,50);
        f.setUndecorated(true);
        f.getContentPane().setBackground(Color.WHITE);


        f.setVisible(true);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String output = text1.getText();
        if (!output.isEmpty()) {
            JPanel p2 = formatLabel(output);

            a1.setLayout(new BorderLayout());
            JPanel right = new JPanel(new BorderLayout());
            right.add(p2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));
            a1.add(vertical, BorderLayout.PAGE_START);

            try {
                dout.writeUTF(output);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            text1.setText("");
            f.repaint();
            f.invalidate();
            f.validate();
        }
    }

    public static JPanel formatLabel(String output)
    {

        JPanel panel =new JPanel();

    panel.setLayout(new BoxLayout((panel),BoxLayout.Y_AXIS));
    JLabel out=new JLabel("<html><p style=\"width :150px\">"+output+"</p></html>");
    out.setFont(new Font("Tahoma",Font.PLAIN,16));
    out.setBackground(new Color(7,94,84));
    out.setOpaque(true);
    out.setBorder(new EmptyBorder(15,15,15,40));
        out.setForeground(Color.WHITE);
        panel.add(out);

        Calendar cal=Calendar.getInstance();
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
        JLabel timo =new JLabel();
        timo.setText(sdf.format(cal.getTime()));
        panel.add(timo);
    return panel;
    }

    public static void main(String[] args) {
        new server();

        try{
            ServerSocket skt =new ServerSocket(6001);
            while(true)
            {
               Socket s= skt.accept();
                DataInputStream din=new DataInputStream(s.getInputStream());
                 dout=new DataOutputStream(s.getOutputStream())
;
                while(true)
                {
                    String msg=din.readUTF();
                    JPanel panel=formatLabel(msg);
                    JPanel left=new JPanel(new BorderLayout());
                    left.add(panel,BorderLayout.LINE_START);
                    vertical.add(left);
                    f.validate();

                }
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }


}
