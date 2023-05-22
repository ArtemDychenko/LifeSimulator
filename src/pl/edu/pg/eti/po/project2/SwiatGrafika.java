package pl.edu.pg.eti.po.project2;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SwiatGrafika implements ActionListener, KeyListener {

    private JFrame frame;
    private JMenu menu;
    private JMenuItem newGame, load, save, exit;
    private JPanel mainPanel;
    private Dimension dimension;
    private Toolkit toolkit;
    private LogGrafika logGrafika = null;
    private AnimalInfo animalInfo = null;

    private PlanszaGrafika planszaGrafika = null;

    private Swiat swiat;

    private final int SPACE;

    public SwiatGrafika() {

        toolkit = Toolkit.getDefaultToolkit();
        dimension = toolkit.getScreenSize();
        SPACE = dimension.height / 100;

        frame = new JFrame("Virtual world");
        frame.setBounds((dimension.width - 1000) / 2, (dimension.height - 750) / 2, 1000, 750);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        JMenuBar menuBar  = new JMenuBar();
        menu = new JMenu("Menu");
        newGame = new JMenuItem("New game");
        load = new JMenuItem("Load");
        save = new JMenuItem("Save");
        exit = new JMenuItem("Exit");
        newGame.addActionListener(this);
        load.addActionListener(this);
        save.addActionListener(this);
        exit.addActionListener(this);
        menu.add(newGame);
        menu.add(load);
        menu.add(save);
        menu.add(exit);
        menuBar.add(menu);
        frame.setJMenuBar(menuBar);
        frame.setLayout(new CardLayout());

        mainPanel = new JPanel();
        mainPanel.setBackground(Color.GRAY);
        mainPanel.setLayout(null);


        frame.addKeyListener(this);
        frame.add(mainPanel);
        frame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newGame) {
            Log.deleteLog();
            int widthB = Integer.parseInt(JOptionPane.showInputDialog(frame,
                    "Enter size of board X", "20"));
            int heightB = Integer.parseInt(JOptionPane.showInputDialog(frame,
                    "Enter size of board Y", "20"));
            double LOADEDWORLD = Double.parseDouble(JOptionPane.showInputDialog
                    (frame, "World fill factor (from 0 to 1)", "0.5"));
            swiat = new Swiat(widthB, heightB, this);
            swiat.createOrganisms(LOADEDWORLD);
            if (planszaGrafika != null)
                mainPanel.remove(planszaGrafika);
            if (logGrafika != null)
                mainPanel.remove(logGrafika);
            if (animalInfo != null)
                mainPanel.remove(animalInfo);
            startGame();
        }
        if (e.getSource() == load) {
            Log.deleteLog();
            String nameOfFile = JOptionPane.showInputDialog(frame, "Podaj nazwe pliku", "test");
            swiat = Swiat.OdtworzSwiat(nameOfFile);
            swiat.setSwiatGrafika(this);
            planszaGrafika = new PlanszaGrafika(swiat);
            logGrafika = new LogGrafika();
            animalInfo = new AnimalInfo();
            if (planszaGrafika != null)
                mainPanel.remove(planszaGrafika);
            if (logGrafika != null)
                mainPanel.remove(logGrafika);
            if (animalInfo != null)
                mainPanel.remove(animalInfo);
            startGame();
        }
        if (e.getSource() == save) {
            String nameOfFile = JOptionPane.showInputDialog(frame, "Podaj nazwe pliku", "test");
            swiat.ZapiszSwiat(nameOfFile);
            Log.addToLog("Swiat zostal zapisany");
            logGrafika.showLog();
        }
        if (e.getSource() == exit) {
            frame.dispose();
        }
    }


    @Override
    public void keyPressed(KeyEvent e) {
        if (swiat != null && swiat.getIsPauza()) {
            int keyCode = e.getKeyCode();
            if (keyCode == KeyEvent.VK_ENTER) {

            }
           else if (swiat.IsHumanAlive()) {
                if (keyCode == KeyEvent.VK_UP) {
                    swiat.getHuman().setDirection(Organizm.Direction.UP);
                } else if (keyCode == KeyEvent.VK_DOWN) {
                    swiat.getHuman().setDirection(Organizm.Direction.DOWN);
                } else if (keyCode == KeyEvent.VK_LEFT) {
                    swiat.getHuman().setDirection(Organizm.Direction.LEFT);
                } else if (keyCode == KeyEvent.VK_RIGHT) {
                    swiat.getHuman().setDirection(Organizm.Direction.RIGHT);
                } else if (keyCode == KeyEvent.VK_P) {
                    getSwiat().getHuman().getSkill().setEndtPoint(getSwiat().getHuman().getSila());
                    Skill tmpSkill = swiat.getHuman().getSkill();

                    if (tmpSkill.ifIsAvailable()) {
                        tmpSkill.Aktywuj();
                        Log.addToLog("Skill 'Magiczny eliksir' zostal wlaczony (Pozostaly" +
                                " czas trwania wynosi " + tmpSkill.getTime() + " tur)");

                    } else if (tmpSkill.ifActive()) {
                        Log.addToLog("Skill juz zostal aktywowany " + "(Pozostaly" +
                                " czas trwania wynosi " + tmpSkill.getTime() + " tur)");
                        logGrafika.showLog();
                        return;
                    } else {
                        Log.addToLog("Skill mozna wlaczyc tylko po "
                                + tmpSkill.getCooldown() + " turach");
                        logGrafika.showLog();
                        return;
                    }
                } else {
                    Log.addToLog("\nWrong symbol");
                    logGrafika.showLog();
                    return;
                }
            } else if (!swiat.iSHumanAlive && (keyCode == KeyEvent.VK_UP ||
                    keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_LEFT ||
                    keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_P)) {
                    Log.addToLog("Human is die");
                    logGrafika.showLog();
                return;
            } else {
                Log.addToLog("\nWrong symbol");
                logGrafika.showLog();
                return;
            }
            Log.deleteLog();
            swiat.setPauza(false);
            swiat.wykonajTure();
            rysujSwiat();
            swiat.setPauza(true);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }


    private class LogGrafika extends JPanel {
        private String text;
        private final String instriction = "by Artem Dychenko 192441\nArrows - manage human\n" +
                "P - active skill\nEnter - next round\n";
        private JTextArea textArea;

        public LogGrafika() {
            super();
            setBounds(planszaGrafika.getX()  ,
                    mainPanel.getY() + planszaGrafika.getHeight() + SPACE * 3 ,
                    mainPanel.getWidth()  - SPACE * 2 , mainPanel.getHeight() * 2 / 6 - SPACE * 3);
            text = Log.writeLog();
            textArea = new JTextArea(text);
            textArea.setEditable(false);
            setLayout(new CardLayout());

            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setMargin(new Insets(5, 5, 5, 5));
            JScrollPane sp = new JScrollPane(textArea);
            add(sp);
        }

        public void showLog() {
            text = instriction + Log.writeLog();
            textArea.setText(text);
        }
    }


    private class PlanszaGrafika extends JPanel {
        private final int widthB;
        private final int heightB;
        private PolePlanszy[][] polePlanszy;
        private Swiat SWIAT;





        public PlanszaGrafika(Swiat swiat) {
            super();
            setBounds(mainPanel.getX() + SPACE, mainPanel.getY() + SPACE,
                    mainPanel.getHeight() * 4 / 6 - SPACE, mainPanel.getHeight() * 4 / 6 - SPACE);
            SWIAT = swiat;
            this.widthB = swiat.getWidthB();
            this.heightB = swiat.getHeightB();

            polePlanszy = new PolePlanszy[heightB][widthB];
            for (int i = 0; i < heightB; i++) {
                for (int j = 0; j < widthB; j++) {
                    polePlanszy[i][j] = new PolePlanszy(j, i);
                    polePlanszy[i][j].addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (e.getSource() instanceof PolePlanszy) {
                                PolePlanszy tmpPole = (PolePlanszy) e.getSource();
                                if (tmpPole.isEmpty == true) {
                                    ListaOrganizmow listaOrganizmow = new ListaOrganizmow
                                            (tmpPole.getX() + frame.getX(),
                                                    tmpPole.getY() + frame.getY(),
                                                    new point(tmpPole.getPozX(), tmpPole.getPozY()));
                                }
                            }

                        }
                    });
                }
            }
            for (int i = 0; i < heightB; i++) {
                for (int j = 0; j < widthB; j++) {
                    this.add(polePlanszy[i][j]);
                }
            }
            this.setLayout(new GridLayout(heightB, widthB));

        }
            private class PolePlanszy extends JButton {
                private boolean isEmpty;
                private Color kolor;
                private final int curX;
                private final int curY;

                public PolePlanszy(int x, int y) {
                    super();
                    kolor = Color.WHITE;
                    setBackground(kolor);
                    isEmpty = true;
                    curX = x;
                    curY = y;
                }

                public boolean isEmpty() {
                    return isEmpty;
                }

                public void setEmpty(boolean empty) {
                    isEmpty = empty;
                }

                public Color getKolor() {
                    return kolor;
                }

                public void setKolor(Color kolor) {
                    this.kolor = kolor;
                    setBackground(kolor);
                }

                public int getPozX() {
                    return curX;
                }

                public int getPozY() {
                    return curY;
                }

            }

                public void rysujPlansze() {
                    for (int i = 0; i < heightB; i++) {
                        for (int j = 0; j < widthB; j++) {
                            Organizm tmpOrganizm = swiat.getPlansza()[i][j];
                            if (tmpOrganizm != null) {
                                polePlanszy[i][j].setEmpty(false);
                                polePlanszy[i][j].setEnabled(false);
                                polePlanszy[i][j].setKolor(tmpOrganizm.getKolor());
                            } else {
                                polePlanszy[i][j].setEmpty(true);
                                polePlanszy[i][j].setEnabled(true);
                                polePlanszy[i][j].setKolor(Color.WHITE);
                            }
                        }
                    }
                }




        public int getSizeX() {
            return widthB;
        }

        public int getSizeY() {
            return heightB;
        }

        public PolePlanszy[][] getPolaPlanszy() {
            return polePlanszy;
        }

        }

        private class ListaOrganizmow extends JFrame {
            private String[] listaOrg;
            private Organizm.TypOrganizmu[] typOrganizmuList;
            private JList jList;

            public ListaOrganizmow(int x, int y, point place) {
                super("Lista organizmow");
                setBounds(x, y, 100, 300);
                listaOrg = new String[]{"Barszcz Sosnowskiego", "Mlecz", "Guarana", "Trawa",
                        "Wilcze jagody", "Antylopa", "Lis", "Owca", "Wilk", "Zolw", "Cyber owca"};
                typOrganizmuList = new Organizm.TypOrganizmu[]{Organizm.TypOrganizmu.BARSZCZ_SOSNOWSKIEGO,
                        Organizm.TypOrganizmu.MLECZ, Organizm.TypOrganizmu.GUARANA, Organizm.TypOrganizmu.TRAWA,
                        Organizm.TypOrganizmu.WILCZE_JAGODY, Organizm.TypOrganizmu.ANTYLOPA,
                        Organizm.TypOrganizmu.LIS,
                        Organizm.TypOrganizmu.OWCA, Organizm.TypOrganizmu.WILK,
                        Organizm.TypOrganizmu.ZOLW, Organizm.TypOrganizmu.CYBER_OWCA
                };

                jList = new JList(listaOrg);
                jList.setVisibleRowCount(listaOrg.length);
                jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                jList.addListSelectionListener(new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        Organizm creature = SpawnerOrganisms.spawnNewCreation(typOrganizmuList[jList.getSelectedIndex()], swiat, place);
                        swiat.addOrganism(creature);
                        Log.addToLog("Was born " + creature.infoOrganism());
                        rysujSwiat();
                        dispose();
                    }
                });

                JScrollPane sp = new JScrollPane(jList);
                add(sp);

                setVisible(true);
            }

        }

    private class AnimalInfo extends JPanel {
        private final int ILOSC_TYPOW = 12;
        private JButton[] jButtons;


//        private final String title = "List of animals and plants";
        private JTextArea textArea;

        public AnimalInfo() {
            super();

            setBounds(planszaGrafika.getX() + planszaGrafika.getWidth() + SPACE,
                    mainPanel.getY() + SPACE,
                    mainPanel.getWidth() - planszaGrafika.getWidth() - SPACE * 3,
                    mainPanel.getHeight() * 4 / 6 - SPACE);
            setBackground(Color.WHITE);

            setLayout(new GridLayout(12, 1));

            jButtons = new JButton[ILOSC_TYPOW];

            jButtons[0] = new JButton("Barszcz Sosnowskiego");
            jButtons[0].setBackground(new Color(82, 0, 0));

            jButtons[1] = new JButton("Guarana");
            jButtons[1].setBackground(new Color(168, 32, 201));

            jButtons[2] = new JButton("Mlecz");
            jButtons[2].setBackground(new Color(217, 194, 32));

            jButtons[3] = new JButton("Trawa");
            jButtons[3].setBackground(Color.GREEN);

            jButtons[4] = new JButton("Wilcze jagody");
            jButtons[4].setBackground(new Color(0, 0, 0));

            jButtons[5] = new JButton("Antylopa");
            jButtons[5].setBackground(new Color(153, 76, 0));

            jButtons[6] = new JButton("Czlowiek");
            jButtons[6].setBackground(new Color(25, 12, 189));

            jButtons[7] = new JButton("Lis");
            jButtons[7].setBackground(new Color(255, 128, 0));

            jButtons[8] = new JButton("Owca");
            jButtons[8].setBackground(new Color(138, 136, 136));

            jButtons[9] = new JButton("Wilk");
            jButtons[9].setBackground(new Color(64, 64, 64));

            jButtons[10] = new JButton("Zolw");
            jButtons[10].setBackground(new Color(2, 86, 2));

            jButtons[11] = new JButton("Cyber owca");
            jButtons[11].setBackground(new Color(0, 234, 255));

            frame.setLocationRelativeTo(null);
            for (int i = 0; i < ILOSC_TYPOW; i++) {
                jButtons[i].setEnabled(false);
                add(jButtons[i]);
            }

        }
    }


            private void startGame() {
                planszaGrafika = new PlanszaGrafika(swiat);
                mainPanel.add(planszaGrafika);

                logGrafika = new LogGrafika();
                mainPanel.add(logGrafika);

                animalInfo = new AnimalInfo();
                mainPanel.add(animalInfo);

                rysujSwiat();
            }

            public void rysujSwiat() {
                planszaGrafika.rysujPlansze();
                logGrafika.showLog();
                SwingUtilities.updateComponentTreeUI(frame);
                frame.requestFocusInWindow();
            }

            public Swiat getSwiat() {
                return swiat;
            }

            public PlanszaGrafika getPlanszaGraphics() {
                return planszaGrafika;
            }

            public LogGrafika getLogGrafika() {
                return logGrafika;
            }




}


