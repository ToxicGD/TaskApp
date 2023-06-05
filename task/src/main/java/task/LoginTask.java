/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package task;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import task.Tasks;

/**
 *
 * @author Daimer
 */
public class LoginTask extends JFrame{
    
    public LoginTask(){
        this.setSize(500, 600);
        this.setLocationRelativeTo(null);
        //this.setLocation(150, 300); reemplazado por bounds
        //this.setBounds(450, 150, 500, 600); // primeros dos parametros posición, los otros dos, tamaño en pantalla
        this.setDefaultCloseOperation(EXIT_ON_CLOSE); //parametro para centrar la ventana
        this.setTitle("Iniciar Sesión");
        this.getContentPane().setBackground(Color.darkGray);
       
        
        loginComponents();//llamar el panel dentro de la ventana
    }
    
    private void loginComponents(){
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.darkGray);
        this.getContentPane().add(panel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);

        //Jpanel paara ingresar el usuario
        JPanel usernamePanel = new JPanel();
        usernamePanel.setLayout(new BoxLayout(usernamePanel, BoxLayout.Y_AXIS));
        usernamePanel.setBackground(Color.darkGray);

        JLabel usernameLabel = new JLabel("Nombre de usuario:");
        usernameLabel.setForeground(Color.white);
        usernamePanel.add(usernameLabel);

        JTextField usernameField = new JTextField(20);
        usernameField.setFont(usernameField.getFont().deriveFont(Font.PLAIN));

        // Crear el borde redondeado para el campo de texto
        Border border = BorderFactory.createLineBorder(Color.gray);
        Border margin = new EmptyBorder(5, 15, 5, 15);
        Border roundedBorder = BorderFactory.createLineBorder(Color.gray, 2, true);
        usernameField.setBorder(BorderFactory.createCompoundBorder(roundedBorder, margin));
        usernamePanel.add(usernameField);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        // Agregar el componente al panel con las restricciones de GridBagConstraints
        panel.add(usernamePanel, gbc);

        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new BoxLayout(passwordPanel, BoxLayout.Y_AXIS));
        passwordPanel.setBackground(Color.darkGray);
        
        JLabel passwordLabel = new JLabel("Contraseña:");
        passwordPanel.add(passwordLabel);
        passwordLabel.setForeground(Color.white);
        gbc.gridx = 0;
        gbc.gridy = 1;

        // Agregar el componente al panel con las restricciones de GridBagConstraints
        panel.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setFont(passwordField.getFont().deriveFont(Font.PLAIN));

        // Crear el borde redondeado para el campo de contraseña
        passwordField.setBorder(BorderFactory.createCompoundBorder(roundedBorder, margin));

        gbc.gridx = 1;
        gbc.gridy = 1;

        // Agregar el componente al panel con las restricciones de GridBagConstraints
        panel.add(passwordField, gbc);

        JButton loginButton = new JButton("Iniciar sesión");
    loginButton.setFont(loginButton.getFont().deriveFont(Font.BOLD));
    loginButton.setBackground(Color.white);
    loginButton.setForeground(Color.darkGray);
    loginButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.white, 2, true),
            BorderFactory.createEmptyBorder(10, 25, 10, 25)));

    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.gridwidth = 2;
    panel.add(loginButton, gbc);

    loginButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (username.equals("admin") && password.equals("123")) {
                // Acceso concedido - Redireccionar a la ventana "Tasks"
                Tasks tasks = new Tasks();
                tasks.setVisible(true);

                // Cerrar la ventana actual (LoginTask)
                dispose();
            } else {
                // Acceso denegado - Mostrar mensaje de error
                JOptionPane.showMessageDialog(LoginTask.this, "Nombre de usuario o contraseña incorrectos", "Error de inicio de sesión", JOptionPane.ERROR_MESSAGE);
            }
        }
    });
    
    }
}
