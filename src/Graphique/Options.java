package Graphique;

import java.awt.BorderLayout;

/**
 * @author idir
 *
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
							/* La fenêtre des option*/

public class Options extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	JLabel fond = new JLabel(new ImageIcon("ressources/gris.png"));
	JCheckBox boxsound = new JCheckBox("   Sound  ");
	JCheckBox boxmusic = new JCheckBox("   Music  ");
	JButton help = new JButton("Help?");

	//construteur de la JFrame qui initialiser les JCheckBox
	public Options(Menu menu) {
		if (menu != null) {
			menu.setFocusable(false);
			menu.setEnabled(false);
		}
		this.setAlwaysOnTop(true);
		this.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				// TODO Auto-generated method stub
				if (menu != null) {
					menu.setFocusable(true);
					menu.setEnabled(true);
				}

			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setFocusable(true);
		this.setResizable(false);
		this.setTitle("Options");
		this.setLayout(null);

		setBounds(0, 0, 450, 300);
		this.setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setLayout(null);
		contentPane.setBounds(0, 0, 450, 300);

		setContentPane(contentPane);

		boxsound.setBounds(50, 10, 350, 50);
		boxsound.setFont(new java.awt.Font(Font.SERIF, Font.BOLD, 35));
		boxsound.setOpaque(true);
		boxsound.setBackground(new Color(30, 127, 203));
		boxsound.setSelected(menu.sound);
		boxsound.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				menu.sound = boxsound.isSelected();
			}
		});
		contentPane.add(boxsound);

		boxmusic.setBounds(50, 110, 350, 50);
		boxmusic.setFont(new java.awt.Font(Font.SERIF, Font.BOLD, 35));
		boxmusic.setOpaque(true);
		boxmusic.setBackground(new Color(30, 127, 203));
		boxmusic.setSelected(menu.music);
		boxmusic.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				menu.music = boxmusic.isSelected();

			}
		});
		contentPane.add(boxmusic);
		
		help.setBounds(50, 200, 350,50);
		help.setFont(new java.awt.Font(Font.SERIF, Font.BOLD, 35));
		help.setOpaque(true);
		help.setBackground(new Color(30, 127, 203));
		help.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
		        Options.this.setEnabled(false); // equivalent à "setEnabled(false);" tout seul car on est dans une fonction anonyme  "actionPerformed(ActionEvent arg0)" , le this refere dans ce cas a la fonction anonyme. 
                JFrame affiche_help = new JFrame("HELP");
                affiche_help.setAlwaysOnTop(true);
                ///affiche_help.setResizable(false);
                try {
                    affiche_help.setFocusable(true);
                    affiche_help.setSize(600,600);
                    affiche_help.setLocationRelativeTo(null);
                    affiche_help.setAlwaysOnTop(true);
                    affiche_help.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    affiche_help.setLayout(new BorderLayout());
                    affiche_help.setVisible(true);

                    JPanel contentPaneH = new JPanel();
                    contentPaneH.setBounds(0, 0, 600, 600);
                    contentPaneH.setLayout( new BorderLayout());
                    affiche_help.add(contentPaneH);

                    //JLabel affichage_help = new JLabel();
                    JTextArea txtHelp; 
                    txtHelp = new JTextArea(30, 50);
                    //txtHelp.setSize(getPreferredSize());
                    txtHelp.setFont(new Font("Arial Black", Font.BOLD, 15));
                    txtHelp.setLineWrap(true);

                    try {
                        //use buffering, reading one line at a time
                        //FileReader always assumes default encoding is OK!
                        BufferedReader input =  new BufferedReader(new FileReader("ressources/help.txt"));
                        try {
                            String line = null; //not declared within while loop
                            
                            while (( line = input.readLine()) != null){
                                txtHelp.append(line+"\n");
                            }
                        }
                        finally {
                            input.close();
                        }
                    }
                    catch (IOException ex){
                        ex.printStackTrace();
                    }

                    txtHelp.setFont(new Font("Arial Black", Font.BOLD, 15));
                    txtHelp.setEditable(false); 
                    //txtHelp.setSize(getPreferredSize());
                    JScrollPane scrollHelp = new JScrollPane(txtHelp);
                    scrollHelp.setFocusable(true);
                    
                    //scrollHelp.setBounds(0, 0, 600, 600);
                    //scrollHelp.setBounds(0, 0, 600, 600);
                    contentPaneH.add(scrollHelp) ;

                }catch (Exception k) {
                    k.printStackTrace();
                }
				
			}
		});
		contentPane.add(help);
		fond.setBounds(0, 0, 450, 300);
		contentPane.add(fond);

	}

}
