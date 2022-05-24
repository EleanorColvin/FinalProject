import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.awt.Image;

public class GUIClass implements ActionListener{
    private JTextField dataText;
    private Networking client;
    private JLabel img;
    private JLabel title;
    private JLabel date;
    private JLabel explanation;
    private SpaceData data;

    public GUIClass()
    {
        client = new Networking();
        dataText = new JTextField();
        img = new JLabel();
        title = new JLabel();
        date = new JLabel();
        explanation = new JLabel();
        data = null;
        setup();
    }

    private void setup() {
        JFrame frame = new JFrame("Space App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel head = new JLabel("HEADING");
        head.setFont(new Font("Times", Font.PLAIN, 20));
        head.setForeground(Color.MAGENTA);

        JPanel panel1 = new JPanel();
        panel1.add(head);

        JPanel panel2 = new JPanel();
        JLabel zip = new JLabel("Enter date(YYYY-MM-DD): ");
        dataText = new JTextField(8);
        JButton search = new JButton("SEARCH");
        JButton clear = new JButton("CLEAR");
        panel2.add(zip);
        panel2.add(dataText);
        panel2.add(search);
        panel2.add(clear);

        search.addActionListener(this);
        clear.addActionListener(this);

        JPanel panel3 = new JPanel();
        title = new JLabel("");
        date = new JLabel("");
        explanation = new JLabel("");
        img = new JLabel(new ImageIcon("src/placeholder.jpg"));
        panel3.add(title);
        panel3.add(img);
        panel3.add(explanation);
        panel3.add(date);

        JPanel panel4 = new JPanel();

        frame.add(panel1, BorderLayout.NORTH);
        frame.add(panel2, BorderLayout.CENTER);
        frame.add(panel3, BorderLayout.EAST);
        frame.add(panel4, BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);
    }

    private void loadData(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("dateobj " + date);
        String strDate = sdf.format(date);
        System.out.println("Date entered: " + strDate);
        data = client.makeAPICall(strDate);
        title.setText(data.getTitle());
        //date.setText(data.getData());
        explanation.setText(data.getExplanation());
        System.out.println("Data loaded: " + data.getTitle() + ", " + data.getExplanation());

        try {
            URL imageURL = new URL(data.getImgUrl());
            BufferedImage image = ImageIO.read(imageURL);
            ImageIcon icon = new ImageIcon(image);
            Image i = icon.getImage();
            Image resized = i.getScaledInstance(200, 200,  image.SCALE_DEFAULT);
            ImageIcon resizedIcon = new ImageIcon(resized);
            img.setIcon(resizedIcon);
        } catch (IOException e) { }
    }

    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) (e.getSource());
        String text = button.getText();

        if (text.equals("SEARCH"))
        {
            String inputDate = dataText.getText();
            System.out.println("date en:" + inputDate);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = sdf.parse(inputDate);
                System.out.println("d: "+ date);
                loadData(date);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        } else if (text.equals("CLEAR"))
        {
            dataText.setText("");
        }
    }
}
