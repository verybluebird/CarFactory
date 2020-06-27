package com.company;

import com.company.parts.Accessories;
import com.company.parts.Body;
import com.company.parts.Engine;
import com.company.store.*;
import com.company.supply.AccessoriesSupply;
import com.company.supply.BodySupply;
import com.company.supply.EngineSupply;
import com.company.thread.ThreadPool;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Hashtable;

public class Main extends JFrame {
    private JFrame frame;
    private JLabel label;
    private Thread thread;

    public static Configuration configuration = new Configuration("src\\com\\company\\config.txt");
    public static Store<Accessories> accessoriesStore = new Store<>(configuration.getValue("StoreAccessoriesSize"));
    public static Store<Engine> engineStore = new Store<>(configuration.getValue("StoreEngineSize"));
    public static Store<Body> bodyStore = new Store<>(configuration.getValue("StoreBodySize"));
    public static CarStore carStore = new CarStore(configuration.getValue("StoreCarSize"));
    public static ThreadPool threadPool = new ThreadPool(configuration.getValue("Workers"));
    public static final Controller controller = new Controller();


    public static boolean logSale = (configuration.getValue("LogSale") == 1);
    private static AccessoriesSupply[] accessoriesSupply;
    private static EngineSupply engineSupply;
    private static BodySupply bodySupply;
    private static Dealer[] dealer;

    public static void main(String[] args) {
        int accessoriesDeliveryCount = configuration.getValue("AccessoriesSupply");
        accessoriesSupply = new AccessoriesSupply[accessoriesDeliveryCount];
        for (int i = 0; i < accessoriesDeliveryCount; i++)
            accessoriesSupply[i] = new AccessoriesSupply(accessoriesStore, 5000, i * 15000);
        bodySupply = new BodySupply(bodyStore, 2500);
        engineSupply = new EngineSupply(engineStore, 1250);
        int traderCount = configuration.getValue("Dealer");
        dealer = new Dealer[traderCount];
        for (int i = 0; i < traderCount; i++)
            dealer[i] = new Dealer(3000, i, carStore);
        Main window = new Main();
        window.frame.setVisible(true);
    }

    private void update() {
        int accessoriesProduced = 0;
        for (AccessoriesSupply delivery : accessoriesSupply)
            accessoriesProduced += delivery.getCountDelivery();
        String message = String.format("<html>Accessories: Details in store: %d, Total: %d<br>Body: " +
                        "Details in storage: %d, Total: %d<br>Engine: Details in store: %d, Total: %d</html>",
                accessoriesStore.storageSize(), accessoriesProduced,
                bodyStore.storageSize(), bodySupply.getCount(),
                engineStore.storageSize(), engineSupply.getCount());
        label.setText(message);
        label.revalidate();
    }
    public void BackgroundImageJFrame()
    {
        setLayout(new BorderLayout());
        JLabel background=new JLabel(new ImageIcon("C:\\Users\\zatol\\IdeaProjects\\CarFactory\\src\\com\\company\\загруженное.jpg"));
        add(background);
        background.setLayout(new FlowLayout());
    }

    private void initialize() {
        Hashtable<Integer, JLabel> hashtable = new Hashtable<>();
        Hashtable<Integer, JLabel> hashtable1 = new Hashtable<>();
        Hashtable<Integer, JLabel> hashtable2 = new Hashtable<>();
        Hashtable<Integer, JLabel> hashtable3 = new Hashtable<>();
        Color BG_COLOR = new Color(0xE4AEF8);
        frame = new JFrame();
        BackgroundImageJFrame();
         frame.setLocationRelativeTo(null);




        frame.setBounds(100, 100, 400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel jPanel = new JPanel();
        frame.getContentPane().add(jPanel, BorderLayout.NORTH);
        jPanel.setLayout(new GridLayout(0, 1, 0, 0));
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 100, 15000, 2000);
        hashtable.put(100, new JLabel("   Accessories"));
        slider.setLabelTable(hashtable);
        slider.setMajorTickSpacing(1000);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        jPanel.add(slider);
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int value = slider.getValue();
                for (AccessoriesSupply delivery : accessoriesSupply)
                    delivery.setDelay(value);
            }
        });
        JSlider jSlider = new JSlider(JSlider.HORIZONTAL, 100, 15000, 1000);
        hashtable1.put(100, new JLabel("   Body"));
        jSlider.setLabelTable(hashtable1);
        jSlider.setMajorTickSpacing(1000);
        jSlider.setPaintTicks(true);
        jSlider.setPaintLabels(true);
        jPanel.add(jSlider);
        jSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                bodySupply.setDelay(jSlider.getValue());
            }
        });
        JSlider jSlider1 = new JSlider(JSlider.HORIZONTAL, 100, 15000, 500);
        hashtable2.put(100, new JLabel("   Engine"));
        jSlider1.setLabelTable(hashtable2);
        jSlider1.setMajorTickSpacing(1000);
        jSlider1.setPaintTicks(true);
        jSlider1.setPaintLabels(true);
        jPanel.add(jSlider1);
        jSlider1.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                engineSupply.setDelay(jSlider1.getValue());
            }
        });
        JSlider jSlider2 = new JSlider(JSlider.HORIZONTAL, 100, 15000, 500);
        hashtable3.put(100, new JLabel("   Dealer"));
        jSlider2.setLabelTable(hashtable3);
        jSlider2.setMajorTickSpacing(1000);
        jSlider2.setPaintTicks(true);
        jSlider2.setPaintLabels(true);
        jPanel.add(jSlider2);
        jSlider2.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int value = jSlider2.getValue();
                for (Dealer item : dealer) item.setDelay(value);
            }
        });
        JPanel panel = new JPanel();
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(new GridLayout(0, 1, 0, 0));
        label = new JLabel("Wait...");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label);
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.interrupted()) {
                    try {
                        Thread.sleep(500);
                        EventQueue.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                Main.this.update();
                            }
                        });
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            }
        });
        thread.start();
        frame.addWindowListener(new WindowAdapter() {
            public void windowClose(WindowEvent windowEvent) {
                thread.interrupt();
                threadPool.terminate();
                controller.terminate();
                for (AccessoriesSupply supply : accessoriesSupply)
                    supply.terminate();
                bodySupply.terminate();
                engineSupply.terminate();
                for (Dealer value : dealer)
                    value.terminate();
                System.exit(0);
            }
        });
    }

    public Main() {
        initialize();

    }

}
