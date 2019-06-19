
package br.com.danubio.apps;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

public class HorseRideView {
    private static final String DEMO_FILE = "demos/";
    
    private HorseRideController horseController;
    private DemoController demoController;
    
    private JFileChooser fileChooser;
    private JPanel drawPanel;
    private Controller controller;
    private JFrame window;
    private JDialog aboutDialog;
    
    public HorseRideView() 
    {
        window = new JFrame("Passeio do Cavalo");
        horseController = new HorseRideController(this);
        demoController = new DemoController(this);
        
        this.controller = horseController;
        
        drawPanel = new JPanel(){
            {
                this.setFocusable(true);
                
                this.addMouseMotionListener(new MouseMotionListener() {
                    @Override
                    public void mouseDragged(MouseEvent e) {
                        mouseMoved(e);
                    }

                    @Override
                    public void mouseMoved(MouseEvent e) {
                        controller.mouseMoved(e.getPoint());
                    }
                    
                });
                
                this.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        controller.mouseClicked(e.getPoint());
                    }
                });
            }
            
            @Override
            public void paintComponent(Graphics g) 
            {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D)g;
                controller.drawHorseRide(g2d);
            }
        };
        
        fileChooser = new JFileChooser();
        
        JMenuBar menuBar = new JMenuBar();
        
        JMenu fileMenu = new JMenu("Arquivo");
        
        JMenuItem openMenuItem = new JMenuItem("Abrir");
        openMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event)
            {
                int result = fileChooser.showOpenDialog(window);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    showDemo(loadFile(file));
                }
            }
        });
        
        JMenuItem exitMenuItem = new JMenuItem("Sair");
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event)
            {
                System.exit(0);
            }
        });
        
        
        JMenu demoMenu = new JMenu("Demostração");
        JMenuItem oneMenuItem = new JMenuItem("heuristic (1, 2)");
        oneMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event)
            {
                showDemo(loadFile("heuristic01.ser"));
            }
        });
        JMenuItem twoMenuItem = new JMenuItem("heuristic (2, 3)");
        twoMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event)
            {
                showDemo(loadFile("heuristic12.ser"));
            }
        });
        JMenuItem threeMenuItem = new JMenuItem("heuristic (2, 7)");
        threeMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event)
            {
                showDemo(loadFile("heuristic16.ser"));
            }
        });
        JMenuItem fourMenuItem = new JMenuItem("heuristic (4, 6)");
        fourMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event)
            {
                showDemo(loadFile("heuristic35.ser"));
            }
        });
        
        JMenu helpMenu = new JMenu("Ajuda");
        JMenuItem helpItem = new JMenuItem("Sobre o Passeio do Cavalo");
        helpItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event)
            {
                showAboutDialog();
            }
        });
        helpMenu.add(helpItem);
        demoMenu.add(oneMenuItem);
        demoMenu.add(twoMenuItem);
        demoMenu.add(threeMenuItem);
        demoMenu.add(fourMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(exitMenuItem);
        menuBar.add(fileMenu);
        menuBar.add(demoMenu);
        menuBar.add(helpMenu);
        
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(800, 750);
        window.add(drawPanel);
        window.setJMenuBar(menuBar);
        window.setVisible(true);
    }
    
    public void repaint()
    {
        drawPanel.repaint();
    }
    
    public void setController(Controller controller)
    {
        this.controller = controller;
    }
    
    public Controller getHorseRideController()
    {
        return horseController;
    }
    
    public Controller getDemoController()
    {
        return demoController;
    }
    
    public void saveHorseRide(List<Point> points)
    {
        Object[] options = {"Confirmar", "Cancelar"};
        
        int result = JOptionPane.showOptionDialog(window, "Você completou o passeio.\nDeseja salvar?", 
          "Save - Passeio do Cavalo", JOptionPane.DEFAULT_OPTION,
          JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        
        if (result == 0) {
            result = fileChooser.showSaveDialog(window);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();

                try {
                    FileOutputStream fileStream = new FileOutputStream(file);
                    ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);

                    objectStream.writeObject(points);

                    fileStream.close();
                    objectStream.close();
                } catch (IOException exception) {
                    JOptionPane.showMessageDialog(window, exception.getMessage(), "Error - Passeio do Cavalo", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    private void showDemo(List<Point> list)
    {
        if (list != null) {
            demoController.setList(list);
            setController(getDemoController());
        }
    }
    
    private List<Point> loadFile(String fileName)
    {
        List<Point> points = null;
        
        try {
            InputStream resource = Settings.class.getClassLoader().getResourceAsStream(DEMO_FILE + fileName);
            ObjectInputStream input = new ObjectInputStream(resource);

            points = (ArrayList<Point>) input.readObject();
            
            resource.close();
            input.close();

        }
        catch (IOException | ClassNotFoundException  exception)
        {
            exception.printStackTrace();
        }
        
        return points;
    }
    
    private List<Point> loadFile(File file)
    {
        List<Point> points = null;
        
        try {
            FileInputStream inputFile = new FileInputStream(file);
            ObjectInputStream inputStream = new ObjectInputStream(inputFile);
            
            points = (ArrayList<Point>) inputStream.readObject();
            
            inputFile.close();
            inputStream.close();
        }
        catch (IOException | ClassNotFoundException exception)
        {
            JOptionPane.showMessageDialog(window, "Error: O conteúdo do arquivo não é conhecido!", "Error - Passeio do Cavalo", JOptionPane.ERROR_MESSAGE);
        }
        
        return points;
    }
    
    
    void showAboutDialog()
    {
        if (aboutDialog == null) {
            this.aboutDialog = new JDialog(window);
            aboutDialog.setTitle("Sobre o Passeio do Cavalo");
            aboutDialog.setLayout(new BorderLayout());

            JTabbedPane aboutTabbedPane = new JTabbedPane();
            JPanel aboutTurtleGraphics = new JPanel();
            JLabel aboutTurtleLabel = new JLabel();
            aboutTurtleLabel.setText("<html><b><u>Passeio do Cavalo</u></b><br><br>"
              + "Versão: 1.0-SNAPSHOT<br><br>"
              + "Um simples programa para passear com o cavalo no tabuleiro vazio.<br>"
              + "O desafio está em percorrer o tabuleiro e ocupar cada casa somente uma vez.<br>"
              + "Cada novo movimento o cavalo exibe os movimentos que poderão ser percorrido.<br>"
              + "Quando o usuário finalizar o passeio o programa pode salvar o passeio em um arquivo.<br>"
              + "É possível visualizar este passeio futuramente apenas abrindo o arquivo com o passeio salvo.<br><br>"
              + "Contato: <a href=mailto:jaimefritschi@gmail.com>jaimefritschi@gmail.com</a></html>");

            aboutTurtleLabel.setHorizontalAlignment(JLabel.LEFT);
            aboutTurtleGraphics.setLayout(new GridLayout(1, 1));
            aboutTurtleGraphics.add(aboutTurtleLabel);

            aboutTabbedPane.addTab("Sobre", aboutTurtleGraphics);

            JPanel aboutAutorPanel = new JPanel();
            JLabel aboutAutorLabel = new JLabel();
            aboutAutorLabel.setHorizontalTextPosition(JLabel.LEFT);
            aboutAutorLabel.setVerticalTextPosition(JLabel.NORTH);
            aboutAutorLabel.setText("<html><b><u>Jaime Eli Fritschi</u></b><br><br><br>"
              + "Relatar erros, novas funcionalidades ou entrar em contato com o desenvolvedor use o email: <br><br>"
              + "<a href=mailto:jaimefritschi@gmail.com>jaimefritschi@gmail.com</a></html>");

            aboutAutorPanel.setLayout(new GridLayout(1, 1));
            aboutAutorPanel.add(aboutAutorLabel);

            aboutTabbedPane.addTab("Sobre o Autor", aboutAutorPanel);

            JButton closeButton = new JButton("Fechar");
            closeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    aboutDialog.dispose();
                }
            });

            JPanel forCloseButton = new JPanel();
            forCloseButton.setLayout(new FlowLayout(FlowLayout.RIGHT));
            forCloseButton.add(closeButton);

            aboutDialog.add(aboutTabbedPane, BorderLayout.CENTER);
            aboutDialog.add(forCloseButton, BorderLayout.PAGE_END);

            aboutDialog.setModalityType(JDialog.ModalityType.APPLICATION_MODAL);
            aboutDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            aboutDialog.setSize(400, 300);
        }
        
        aboutDialog.setVisible(true);
    }
    
}
