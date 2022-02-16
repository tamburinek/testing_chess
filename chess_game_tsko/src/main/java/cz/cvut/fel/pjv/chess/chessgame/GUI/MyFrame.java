package cz.cvut.fel.pjv.chess.chessgame.GUI;

import cz.cvut.fel.pjv.chess.chessgame.board.Game;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class MyFrame extends JFrame implements ActionListener {

    //region player1
    private final Container container;
    private final JLabel title;
    private final JLabel name;
    private final JTextField nameText;
    private final JLabel mobile;
    private JTextField mobileText;
    private final JLabel gender;
    private final JRadioButton male;
    private final JRadioButton female;
    private ButtonGroup genderGroup;
    private JLabel age;
    private JTextField ageText;
    private JLabel address;
    private JTextArea addressText;
    private JCheckBox player2;
    private JButton submit;
    private final JButton edit;

    //region player2
    private final JLabel name1;
    private JTextField nameText1;
    private JLabel mobile1;
    private JTextField mobileText1;
    private final JLabel gender1;
    private final JRadioButton male1;
    private final JRadioButton female1;
    private ButtonGroup genderGroup1;
    private JLabel age1;
    private JTextField ageText1;
    private JLabel address1;
    private JTextArea addressText1;
    private JButton submit1;
    private final JButton edit1;
    //endregion

    //region constants
    private final String[] dates = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15",
            "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" };

    private final String[] months = { "Jan", "feb", "Mar", "Apr", "May", "Jun", "July", "Aug", "Sup", "Oct", "Nov", "Dec" };

    private final String[] years = { "1995", "1996", "1997", "1998", "1999", "2000", "2001", "2002",
            "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014",
            "2015", "2016", "2017", "2018", "2019" };
    //endregion

    // constructor, to initialize the components with default values
    public MyFrame() {
        setTitle("Chess");
        setBounds(300, 90, 900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        container = getContentPane();
        container.setLayout(null);


        title = new JLabel("Welcome to the game!");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setSize(400, 35);
        title.setLocation(300, 30);
        container.add(title);

        //region player 1
        name = new JLabel("Name");
        name.setFont(new Font("Arial", Font.PLAIN, 20));
        name.setSize(100, 20);
        name.setLocation(100, 100);
        container.add(name);

        nameText = new JTextField();
        nameText.setFont(new Font("Arial", Font.PLAIN, 15));
        nameText.setSize(190, 20);
        nameText.setLocation(200, 100);
        container.add(nameText);

        mobile = new JLabel("Mobile");
        mobile.setFont(new Font("Arial", Font.PLAIN, 20));
        mobile.setSize(100, 20);
        mobile.setLocation(100, 150);
        container.add(mobile);

        mobileText = new JTextField();
        mobileText.setFont(new Font("Arial", Font.PLAIN, 15));
        mobileText.setSize(190, 20);
        mobileText.setLocation(200, 150);
        container.add(mobileText);

        gender = new JLabel("Gender");
        gender.setFont(new Font("Arial", Font.PLAIN, 20));
        gender.setSize(100, 20);
        gender.setLocation(100, 200);
        container.add(gender);

        male = new JRadioButton("Male");
        male.setFont(new Font("Arial", Font.PLAIN, 15));
        male.setSelected(true);
        male.setSize(75, 20);
        male.setLocation(200, 200);
        container.add(male);

        female = new JRadioButton("Female");
        female.setFont(new Font("Arial", Font.PLAIN, 15));
        female.setSelected(false);
        female.setSize(80, 20);
        female.setLocation(275, 200);
        container.add(female);

        genderGroup = new ButtonGroup();
        genderGroup.add(male);
        genderGroup.add(female);

        age = new JLabel("Age");
        age.setFont(new Font("Arial", Font.PLAIN, 20));
        age.setSize(100, 20);
        age.setLocation(100, 250);
        container.add(age);

        ageText = new JTextField();
        ageText.setFont(new Font("Arial", Font.PLAIN, 15));
        ageText.setSize(190, 20);
        ageText.setLocation(200, 250);
        container.add(ageText);

        address = new JLabel("Address");
        address.setFont(new Font("Arial", Font.PLAIN, 20));
        address.setSize(100, 20);
        address.setLocation(100, 300);
        container.add(address);

        addressText = new JTextArea();
        addressText.setFont(new Font("Arial", Font.PLAIN, 15));
        addressText.setSize(190, 75);
        addressText.setLocation(200, 300);
        addressText.setLineWrap(true);
        container.add(addressText);

        player2 = new JCheckBox("2 player game");
        player2.setFont(new Font("Arial", Font.PLAIN, 15));
        player2.setSize(250, 20);
        player2.setLocation(150, 400);
        player2.addActionListener(this);
        container.add(player2);

        submit = new JButton("Submit");
        submit.setFont(new Font("Arial", Font.PLAIN, 15));
        submit.setSize(100, 20);
        submit.setLocation(150, 450);
        submit.addActionListener(this);
        container.add(submit);

        edit = new JButton("Reset");
        edit.setFont(new Font("Arial", Font.PLAIN, 15));
        edit.setSize(100, 20);
        edit.setLocation(270, 450);
        edit.addActionListener(this);
        container.add(edit);
        //endregion

        //region player 2
        name1 = new JLabel("Name");
        name1.setFont(new Font("Arial", Font.PLAIN, 20));
        name1.setSize(100, 20);
        name1.setLocation(100 + 200 + 150, 100);
        name1.setVisible(false);
        container.add(name1);

        nameText1 = new JTextField();
        nameText1.setFont(new Font("Arial", Font.PLAIN, 15));
        nameText1.setSize(190, 20);
        nameText1.setLocation(200 + 200 + 150, 100);
        nameText1.setVisible(false);
        container.add(nameText1);

        mobile1 = new JLabel("Mobile");
        mobile1.setFont(new Font("Arial", Font.PLAIN, 20));
        mobile1.setSize(100, 20);
        mobile1.setLocation(100 + 200 + 150, 150);
        mobile1.setVisible(false);
        container.add(mobile1);

        mobileText1 = new JTextField();
        mobileText1.setFont(new Font("Arial", Font.PLAIN, 15));
        mobileText1.setSize(190, 20);
        mobileText1.setLocation(200 + 200 + 150, 150);
        mobileText1.setVisible(false);
        container.add(mobileText1);

        gender1 = new JLabel("Gender");
        gender1.setFont(new Font("Arial", Font.PLAIN, 20));
        gender1.setSize(100, 20);
        gender1.setLocation(100 + 200 + 150, 200);
        gender1.setVisible(false);
        container.add(gender1);

        male1 = new JRadioButton("Male");
        male1.setFont(new Font("Arial", Font.PLAIN, 15));
        male1.setSelected(true);
        male1.setSize(75, 20);
        male1.setLocation(200 + 200 + 150, 200);
        male1.setVisible(false);
        container.add(male1);

        female1 = new JRadioButton("Female");
        female1.setFont(new Font("Arial", Font.PLAIN, 15));
        female1.setSelected(false);
        female1.setSize(80, 20);
        female1.setLocation(275 + 200 + 150, 200);
        female1.setVisible(false);
        container.add(female1);

        genderGroup1 = new ButtonGroup();
        genderGroup1.add(male1);
        genderGroup1.add(female1);

        age1 = new JLabel("Age");
        age1.setFont(new Font("Arial", Font.PLAIN, 20));
        age1.setSize(100, 20);
        age1.setLocation(100 + 200 + 150, 250);
        age1.setVisible(false);
        container.add(age1);

        ageText1 = new JTextField();
        ageText1.setFont(new Font("Arial", Font.PLAIN, 15));
        ageText1.setSize(190, 20);
        ageText1.setLocation(200 + 200 + 150, 250);
        ageText1.setVisible(false);
        container.add(ageText1);

        address1 = new JLabel("Address");
        address1.setFont(new Font("Arial", Font.PLAIN, 20));
        address1.setSize(100, 20);
        address1.setLocation(100 + 200 + 150, 300);
        address1.setVisible(false);
        container.add(address1);

        addressText1 = new JTextArea();
        addressText1.setFont(new Font("Arial", Font.PLAIN, 15));
        addressText1.setSize(190, 75);
        addressText1.setLocation(200 + 200 + 150, 300);
        addressText1.setLineWrap(true);
        addressText1.setVisible(false);
        container.add(addressText1);

        submit1 = new JButton("Submit");
        submit1.setFont(new Font("Arial", Font.PLAIN, 15));
        submit1.setSize(100, 20);
        submit1.setLocation(150 + 200 + 150, 450);
        submit1.addActionListener(this);
        submit1.setVisible(false);
        container.add(submit1);

        edit1 = new JButton("Reset");
        edit1.setFont(new Font("Arial", Font.PLAIN, 15));
        edit1.setSize(100, 20);
        edit1.setLocation(270 + 200 + 150, 450);
        edit1.addActionListener(this);
        edit1.setVisible(false);
        container.add(edit1);
        //endregion

        setVisible(true);
    }

    // method actionPerformed()
    // to get the action performed
    // by the user and act accordingly
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == submit) {
            Game game = new Game();
            game.run();
            dispose();
        } else if (e.getSource() == player2) {
            name1.setVisible(!name1.isVisible());
            nameText1.setVisible(!nameText1.isVisible());
            mobile1.setVisible(!mobile1.isVisible());
            mobileText1.setVisible(!mobileText1.isVisible());
            gender1.setVisible(!gender1.isVisible());
            male1.setVisible(!male1.isVisible());
            female1.setVisible(!female1.isVisible());
            age1.setVisible(!age1.isVisible());
            ageText1.setVisible(!ageText1.isVisible());
            address1.setVisible(!address1.isVisible());
            addressText1.setVisible(!addressText1.isVisible());
            submit1.setVisible(!submit1.isVisible());
            edit1.setVisible(!edit1.isVisible());
        }
    }
}